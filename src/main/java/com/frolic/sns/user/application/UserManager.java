package com.frolic.sns.user.application;

import com.frolic.sns.auth.application.security.JwtProvider;
import com.frolic.sns.post.repository.LikeDslRepository;
import com.frolic.sns.post.repository.PostRepository;
import com.frolic.sns.user.application.info.UserEmail;
import com.frolic.sns.user.application.info.UserName;
import com.frolic.sns.user.dto.UserInfo;
import com.frolic.sns.user.dto.UserUnitedInfo;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.FollowRepository;
import com.frolic.sns.user.repository.UserDslRepository;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public final class UserManager {

  private final UserRepository repository;
  private final UserDslRepository dslRepository;
  private final JwtProvider jwtProvider;
  private final FollowRepository followRepository;
  private final PostRepository postRepository;
  private final LikeDslRepository likeDslRepository;


  public User getUserByHttpRequest(HttpServletRequest request) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    String email = jwtProvider.getUserEmailByToken(token);
    return dslRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  public User getUser(UserName userName) {
    return dslRepository.findByUsername(userName.getUsername()).orElseThrow(UserNotFoundException::new);
  }

  public User getUser(UserEmail userEmail) {
    return dslRepository.findUserByEmail(userEmail.getEmail()).orElseThrow(UserNotFoundException::new);
  }

  public UserUnitedInfo getUserUnitedInfo(User user) {
    long followerCount = followRepository.countByFollowingId(user);
    long followingCount = followRepository.countByFollowerId(user);
    long postCount = postRepository.countAllByUser(user);
    long likeCount = likeDslRepository.countAllLike(user);
    UserInfo userInfo = UserInfo.from(user);

    return UserUnitedInfo.builder()
      .addAllFollowerCount(followerCount)
      .addAllFollowingCount(followingCount)
      .addAllGivenLikeCount(likeCount)
      .addAllPostCount(postCount)
      .addUserInfo(userInfo)
      .build();
  }

}
