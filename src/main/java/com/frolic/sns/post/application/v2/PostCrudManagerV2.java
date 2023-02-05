package com.frolic.sns.post.application.v2;

import com.frolic.sns.global.common.file.application.FileManageable;
import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.global.common.jwt.JwtEntityLoader;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostFile;
import com.frolic.sns.post.repository.*;
import com.frolic.sns.user.dto.UserInfo;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostCrudManagerV2 {

  private final HashTagManager hashTagManager;
  private final PostRepository postRepository;
  private final PostFileDslRepository postFileDslRepository;
  private final JwtEntityLoader entityLoader;

  public PostInfo createPost(String token, CreatePostRequest createPostRequest) {
    User user = entityLoader.getUser(token);
    List<String> hashtags = createPostRequest.getHashtags();
    Post newPost = commitAndReturnPost(createPostRequest, user);
    hashTagManager.connectHashtagsWithPost(hashtags, newPost);
    List<PostFile> createdPostFiles = postFileDslRepository.createPostFilesByIds(newPost, createPostRequest.getImageIds());
    List<FileInfo> fileInfos = createdPostFiles.stream().map((postFile) -> {
        ApplicationFile file = postFile.getFile();
        log.warn("filename: {}", file.getName());
        return FileInfo.builder()
          .addId(file.getId())
          .addDownloadUrl(file.getDownloadUrl())
          .build();
      }
    )
      .collect(Collectors.toList());

    return getInitializedPostInfoBuilder(newPost)
      .addFiles(fileInfos)
      .addHashtags(hashtags)
      .addUserInfo(UserInfo.from(user))
      .build();
  }

  private Post commitAndReturnPost(CreatePostRequest createPostRequest, User user) {
    Post buildedPost = Post.builder()
      .addUser(user)
      .addTextContent(createPostRequest.getTextContent())
      .build();
    return postRepository.saveAndFlush(buildedPost);
  }

  private PostInfo.PostInfoBuilder getInitializedPostInfoBuilder(Post post) {
    return PostInfo.builder()
      .addId(post.getId())
      .addTextContent(post.getTextContent())
      .addCreatedDate(LocalDateTime.now())
      .addUpdatedDate(LocalDateTime.now())
      .addIsLikeUp(false)
      .addLikeCount(0L)
      .addComments(new ArrayList<>());
  }

}
