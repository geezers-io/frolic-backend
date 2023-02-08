package com.frolic.sns.post.application.v2;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.global.common.file.repository.FileRepository;
import com.frolic.sns.global.exception.NotFoundResourceException;
import com.frolic.sns.post.dto.CommentInfo;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.model.Hashtag;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostFile;
import com.frolic.sns.post.model.PostHashTag;
import com.frolic.sns.post.repository.*;
import com.frolic.sns.user.dto.UserInfo;
import com.frolic.sns.user.exception.NotPermissionException;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UpdatePostBusinessManager {
  private final PostHashtagRepository postHashtagRepository;
  private final HashtagDslRepository hashtagDslRepository;

  private final PostFileDslRepository postFileDslRepository;
  private final PostFileRepository postFileRepository;
  private final FileRepository fileRepository;
  private final LikeRepository likeRepository;
  private final CommentRepository commentRepository;

  public void checkUserPermission(User requestUser, User postOwnedUser) {
    boolean isSameUser = postOwnedUser.getId().equals(requestUser.getId());
    if (!isSameUser) throw new NotPermissionException();
  }

  public void updateHashtags(Post post, List<String> hashtags) {
    List<PostHashTag> postHashTags = hashtagDslRepository.getAllPostHashtagByPostId(post.getId());
    List<PostHashTag> garbagePostHashtags = postHashTags.stream()
      .filter(postHashTag -> !hashtags.contains(postHashTag.getHashtag().getName()))
      .collect(Collectors.toList());
    postHashtagRepository.deleteAll(garbagePostHashtags);
    List<Hashtag> searchedHashtags = hashtagDslRepository.createHashtagsIfNotExists(hashtags);

    searchedHashtags.forEach(hashtag -> {
      if (!postHashtagRepository.existsByPostAndHashtag(post, hashtag)) {
        PostHashTag newPostHashtag = PostHashTag.builder()
          .addPost(post)
          .addHashtag(hashtag)
          .build();
        postHashtagRepository.save(newPostHashtag);
      }
    });
  }

  public List<FileInfo> updateImages(Post post, List<Long> imageIds) {
    List<Long> existsPostImageIds = postFileDslRepository.getIdsByPostId(post.getId());

    List<Long> trash = existsPostImageIds.stream()
      .filter(id -> !imageIds.contains(id))
      .collect(Collectors.toList());
    postFileDslRepository.removeAllByIds(trash);

    List<PostFile> createdPostFiles = new ArrayList<>();
    imageIds.forEach(imageId -> {
      if (!postFileDslRepository.exists(post, imageId)) {
        ApplicationFile applicationFile = fileRepository.findById(imageId).orElseThrow(NotFoundResourceException::new);
        PostFile newPostFile = PostFile.builder().addFile(applicationFile).addPost(post).build();
        createdPostFiles.add(newPostFile);
      }
    });

    return postFileRepository.saveAll(createdPostFiles).stream().map(postFile -> {
      ApplicationFile file = postFile.getFile();
      return FileInfo.builder()
        .addId(file.getId())
        .addDownloadUrl(file.getDownloadUrl())
        .addFilename(file.getName())
        .build();
    }).collect(Collectors.toList());
  }

  public PostInfo.PostInfoBuilder getBuilder(Post post, User user) {
    UserInfo userInfo = UserInfo.from(user);
    boolean isLikeUp = likeRepository.existsByPostAndUser(post, user);
    List<CommentInfo> comments = commentRepository.findAllByPost(post)
      .stream()
      .map(comment ->
          CommentInfo.builder()
            .addId(comment.getId())
            .addPostId(post.getId())
            .addUserInfo(userInfo)
            .addReplyUserId(null)
            .addTextContent(comment.getTextContent())
            .build()
        )
      .collect(Collectors.toList());
    long likeCount = likeRepository.countAllByPost(post);

    return PostInfo.builder()
      .addId(post.getId())
      .addTextContent(post.getTextContent())
      .addCreatedDate(post.getCreatedDate())
      .addUpdatedDate(post.getUpdatedDate())
      .addIsLikeUp(isLikeUp)
      .addLikeCount(likeCount)
      .addUserInfo(userInfo)
      .addComments(comments);
  }

}
