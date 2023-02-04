package com.frolic.sns.post.application.v2;

import com.frolic.sns.global.common.file.application.CustomFile;
import com.frolic.sns.global.common.file.application.FileManageable;
import com.frolic.sns.global.common.jwt.JwtEntityLoader;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.global.exception.NotFoundResourceException;
import com.frolic.sns.post.dto.PostInfo;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.model.Hashtag;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostHashTag;
import com.frolic.sns.post.repository.HashtagRepository;
import com.frolic.sns.post.repository.PostFileRepository;
import com.frolic.sns.post.repository.PostHashtagRepository;
import com.frolic.sns.post.repository.PostRepository;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCrudManagerV2 {

  private final HashTagManager hashTagManager;
  private final UserRepository userRepository;
  private final FileManageable fileManager;
  private final PostRepository postRepository;
  private final PostFileRepository postFileRepository;
  private final JwtProvider jwtProvider;
  private final HashtagRepository hashtagRepository;
  private final PostHashtagRepository postHashtagRepository;
  private final JwtEntityLoader entityLoader;

  public PostInfo createPost(String token, CreatePostRequest createPostRequest) {
    User user = entityLoader.getUser(token);
    List<String> hashtags = createPostRequest.getHashtags();
    Post newPost = commitAndReturnPost(createPostRequest, user);
    hashTagManager.connectHashtagsWithPost(hashtags, newPost);
    return null;
  }

  private Post commitAndReturnPost(CreatePostRequest createPostRequest, User user) {
    Post buildedPost = Post.builder()
      .addUser(user)
      .addTextContent(createPostRequest.getTextContent())
      .build();
    return postRepository.saveAndFlush(buildedPost);
  }

}
