package com.modular.restfulserver.user.application;

import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.exception.AlreadyExistsFollowException;
import com.modular.restfulserver.user.model.Follow;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.FollowRepository;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFollowManagerImpl implements UserFollowManager {

  private final UserRepository userRepository;
  private final FollowRepository followRepository;
  private final JwtProvider jwtProvider;

  @Override
  public String[] getFollowerListBySelf(String token) {
    User tokenUser = getUserByParsingToken(token);
    return followRepository.getAllByFollowerId(tokenUser);
  }

  @Override
  public String[] getFollowerListByUsername(String token, String username) {
    User user = getUserByUsername(username);
    return followRepository.getAllByFollowerId(user);
  }

  @Override
  public String[] getFollowingListBySelf(String token) {
    User tokenUser = getUserByParsingToken(token);
    return followRepository.getAllByFollowingId(tokenUser);
  }

  @Override
  public String[] getFollowingListByUsername(String token, String username) {
    User user = getUserByUsername(username);
    return followRepository.getAllByFollowingId(user);
  }

  @Override
  public void addFollowToUsername(String token, String username) {
    User tokenUser = getUserByParsingToken(token);
    User usernameUser = getUserByUsername(username);
    boolean isExistsFollow = followRepository
      .existsFollowByFollowerIdAndFollowingId(tokenUser, usernameUser);
    if (isExistsFollow)
      throw new AlreadyExistsFollowException();

    Follow newFollow = new Follow();
    newFollow.setFollowerId(tokenUser);
    newFollow.setFollowingId(usernameUser);
    followRepository.save(newFollow);
  }

  @Override
  public void removeFollowToUsername(String token, String username) {
    User tokenUser = getUserByParsingToken(token);
    User usernameUser = getUserByUsername(username);
    followRepository.deleteFollowByFollowerIdAndFollowingId(
      tokenUser,
      usernameUser
    );
  }

  private User getUserByParsingToken(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
  }

  private User getUserByUsername(String username) {
    return userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
  }

}
