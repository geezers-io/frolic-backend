package com.frolic.sns.user.application;

import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.user.dto.FollowUserRequest;
import com.frolic.sns.user.exception.AlreadyExistsFollowException;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.Follow;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.FollowRepository;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFollowManager implements UserFollowManageable {

  private final UserRepository userRepository;
  private final FollowRepository followRepository;
  private final JwtProvider jwtProvider;

  @Override
  public List<FollowUserRequest> getFollowerListBySelf(String token) {
    User tokenUser = getUserByParsingToken(token);
    return followRepository.findAllNameByUserFollowerInfo(tokenUser).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  @Override
  public List<FollowUserRequest> getFollowerListByUsername(String username) {
    User user = getUserByUsername(username);
    return followRepository.findAllFollowerUserByUsername(user).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  @Override
  public List<FollowUserRequest> getFollowingListBySelf(String token) {
    User tokenUser = getUserByParsingToken(token);
    return followRepository.findAllNameByUserFollowingInfo(tokenUser).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  @Override
  public List<FollowUserRequest> getFollowingListByUsername(String username) {
    User user = getUserByUsername(username);
    return followRepository.findAllFollowingUserByUsername(user).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  @Override
  public void addFollowToUsername(String token, String username) {
    User tokenUser = getUserByParsingToken(token);
    User usernameUser = getUserByUsername(username);
    boolean isExistsFollow = followRepository
      .existsFollowByFollowerIdAndFollowingId(usernameUser, tokenUser);
    if (isExistsFollow)
      throw new AlreadyExistsFollowException();

    Follow newFollow = new Follow();
    newFollow.setFollowerId(usernameUser);
    newFollow.setFollowingId(tokenUser);
    followRepository.save(newFollow);
  }

  @Override
  public void removeFollowToUsername(String token, String username) {
    User tokenUser = getUserByParsingToken(token);
    User usernameUser = getUserByUsername(username);
    followRepository.deleteFollowByFollowerIdAndFollowingId(usernameUser, tokenUser);
  }

  @Override
  public boolean checkExistsFollow(String token, String username) {
    User tokenUser = getUserByParsingToken(token);
    User targetUser = getUserByUsername(username);
    return followRepository.existsFollowByFollowerIdAndFollowingId(targetUser, tokenUser);
  }

  @Override
  public boolean checkExistsFollowing(String token, String username) {
    User tokenUser = getUserByParsingToken(token);
    User targetUser = getUserByUsername(username);
    return followRepository.existsFollowByFollowerIdAndFollowingId(tokenUser, targetUser);
  }

  private User getUserByParsingToken(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  private User getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
  }

  private FollowUserRequest createFollowUser(User user) {
    return new FollowUserRequest(user.getUsername(), user.getRealname());
  }

}
