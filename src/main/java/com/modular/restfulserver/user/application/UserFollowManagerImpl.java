package com.modular.restfulserver.user.application;

import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.dto.FollowUserDto;
import com.modular.restfulserver.user.exception.AlreadyExistsFollowException;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.Follow;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.FollowRepository;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFollowManagerImpl implements UserFollowManager {

  private final UserRepository userRepository;
  private final FollowRepository followRepository;
  private final JwtProvider jwtProvider;

  @Override
  public List<FollowUserDto> getFollowerListBySelf(String token) {
    User tokenUser = getUserByParsingToken(token);
    return followRepository.findAllNameByUserFollowerInfo(tokenUser).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  @Override
  public List<FollowUserDto> getFollowerListByUsername(String username) {
    User user = getUserByUsername(username);
    return followRepository.findAllFollowerUserByUsername(user).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  @Override
  public List<FollowUserDto> getFollowingListBySelf(String token) {
    User tokenUser = getUserByParsingToken(token);
    return followRepository.findAllNameByUserFollowingInfo(tokenUser).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  @Override
  public List<FollowUserDto> getFollowingListByUsername(String username) {
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

  private FollowUserDto createFollowUser(User user) {
    return new FollowUserDto(user.getUsername(), user.getRealname());
  }

}
