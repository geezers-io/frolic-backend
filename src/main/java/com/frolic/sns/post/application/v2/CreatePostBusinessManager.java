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
import java.util.ArrayList;
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

  public PostInfo.PostInfoBuilder getInitializedPostInfoBuilder(Post post, CreatePostRequest createPostRequest, User user) {
    return PostInfo.builder()
      .addId(post.getId())
      .addHashtags(createPostRequest.getHashtags())
      .addUserInfo(UserInfo.from(user))
      .addTextContent(post.getTextContent())
      .addCreatedDate(LocalDateTime.now())
      .addUpdatedDate(LocalDateTime.now())
      .addIsLikeUp(false)
      .addLikeCount(0L)
      .addComments(new ArrayList<>());
  }

  public List<FileInfo> getFileInfosFromPostFiles(List<PostFile> postFiles) {
    return postFiles.stream().map((postFile) -> {
          ApplicationFile file = postFile.getFile();
          return FileInfo.builder()
            .addId(file.getId())
            .addDownloadUrl(file.getDownloadUrl())
            .build();
        }
      )
      .collect(Collectors.toList());
  }

}
