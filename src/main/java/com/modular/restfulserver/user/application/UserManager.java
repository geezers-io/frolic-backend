package com.modular.restfulserver.user.application;

import com.modular.restfulserver.post.repository.PostRepository;
import com.modular.restfulserver.post.repository.LikeRepository;
import com.modular.restfulserver.auth.exception.AlreadyExistsUserException;
import com.modular.restfulserver.auth.exception.PasswordNotMatchException;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.dto.UserUnitedInfo;
import com.modular.restfulserver.user.dto.UserInfo;
import com.modular.restfulserver.user.dto.PasswordUpdateRequest;
import com.modular.restfulserver.user.dto.UserUpdateRequest;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.FollowRepository;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManager implements UserManageable {

  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final FollowRepository followRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtProvider jwtProvider;

  @Override
  public UserInfo updateUserDetail(String token, UserUpdateRequest userUpdateRequest) {
    User user = getUserByToken(token);

    checkDuplicatedUserDetailWhenModified(userUpdateRequest, user);

    user.changeEmail(userUpdateRequest.getEmail());
    user.changeUsername(userUpdateRequest.getUsername());
    user.changeRealname(userUpdateRequest.getRealname());
    user.changePhoneNumber(userUpdateRequest.getPhoneNumber());
    userRepository.save(user);

    return UserInfo.from(user);
  }

  @Override
  public void updateUserPassword(String token, PasswordUpdateRequest updateRequest) {
    User user = getUserByToken(token);
    boolean isMatchPassword = passwordEncoder.matches(updateRequest.getPrevPassword(), user.getPassword());
    if (!isMatchPassword)
      throw new PasswordNotMatchException();

    String newPassword = passwordEncoder.encode(updateRequest.getNewPassword());
    user.changePassword(newPassword);
    userRepository.save(user);
  }

  @Override
  public void deleteUser(String token, String password) {
    User user = getUserByToken(token);
    if (!passwordEncoder.matches(password, user.getPassword()))
      throw new PasswordNotMatchException();
    userRepository.delete(user);
  }

  @Override
  public UserUnitedInfo getUserUnitedDetail(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    return buildUserUnitedDetail(user);
  }

  @Override
  public UserUnitedInfo getUserUnitedDetailByToken(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userRepository.findByEmail(email)
      .orElseThrow(UserNotFoundException::new);

    return buildUserUnitedDetail(user);
  }

  private UserUnitedInfo buildUserUnitedDetail(User user) {
    long followerCount = followRepository.countByFollowingId(user);
    long followingCount = followRepository.countByFollowerId(user);
    long postCount = postRepository.countAllByUser(user);
    long likeCount = likeRepository.countAllByUser(user);
    UserInfo userInfo = UserInfo.from(user);

    return UserUnitedInfo.builder()
      .addAllFollowerCount(followerCount)
      .addAllFollowingCount(followingCount)
      .addAllGivenLikeCount(likeCount)
      .addAllPostCount(postCount)
      .addUserInfo(userInfo)
      .build();
  }

  private void checkDuplicatedUserDetailWhenModified(UserUpdateRequest dto, User targetUser) {
    boolean isAlreadyExistsEmail = userRepository.existsByEmail(dto.getEmail());
    boolean isAlreadyExistsUsername = userRepository.existsByUsername(dto.getUsername());

    if (isAlreadyExistsEmail && !dto.getEmail().equals(targetUser.getEmail()))
      throw new AlreadyExistsUserException();

    if (isAlreadyExistsUsername && !dto.getUsername().equals(targetUser.getUsername()))
      throw new AlreadyExistsUserException();
  }

  private User getUserByToken(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }

}
