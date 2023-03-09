package com.frolic.sns.post.application.v2;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostFile;
import com.frolic.sns.post.repository.PostRepository;
import com.frolic.sns.user.dto.UserInfo;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreatePostBusinessManager {

  private final PostRepository postRepository;

  public Post commitAndReturnPost(CreatePostRequest createPostRequest, User user) {
    Post buildedPost = Post.builder()
      .addUser(user)
      .addTextContent(createPostRequest.getTextContent())
      .build();
    return postRepository.saveAndFlush(buildedPost);
  }

  public PostInfo getPostInfo(Post post, CreatePostRequest createPostRequest, User user, List<FileInfo> fileInfos) {
    return PostInfo.builder()
      .addId(post.getId())
      .addHashtags(createPostRequest.getHashtags())
      .addUserInfo(UserInfo.from(user))
      .addTextContent(post.getTextContent())
      .addCreatedDate(LocalDateTime.now())
      .addUpdatedDate(LocalDateTime.now())
      .addIsLikeUp(false)
      .addLikeCount(0L)
      .addCommentCount(Long.valueOf(0))
      .addFiles(fileInfos)
      .build();
  }

  public List<FileInfo> getFileInfosFromPostFiles(List<PostFile> postFiles) {
    return postFiles.stream().map(this::createFileInfo).collect(Collectors.toList());
  }

  private FileInfo createFileInfo(PostFile postFile) {
    ApplicationFile file = postFile.getFile();
    return FileInfo.builder()
      .addId(file.getId())
      .build();
  }

}
