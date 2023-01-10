package com.modular.restfulserver.user.application;

import com.modular.restfulserver.post.repository.PostRepository;
import com.modular.restfulserver.post.repository.LikeRepository;
import com.modular.restfulserver.auth.exception.AlreadyExistsUserException;
import com.modular.restfulserver.auth.exception.PasswordNotMatchException;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.dto.UserUnitedDetails;
import com.modular.restfulserver.user.dto.UserDetails;
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
public class UserManagerImpl implements UserManager {

  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final FollowRepository followRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtProvider jwtProvider;

  @Override
  public UserDetails updateUserInfo(String token, UserUpdateRequest dto) {
    User user = getUserByToken(token);

    checkDuplicatedInfo(dto, user);

    user.changeEmail(dto.getEmail());
    user.changeUsername(dto.getUsername());
    user.changeRealname(dto.getRealname());
    userRepository.save(user);

    return UserDetails.from(user);
  }

  @Override
  public void updateUserPassword(String token, PasswordUpdateRequest dto) {
    User user = getUserByToken(token);
    boolean isMatchPassword = passwordEncoder.matches(dto.getPrevPassword(), user.getPassword());
    if (!isMatchPassword)
      throw new PasswordNotMatchException();

    String newPassword = passwordEncoder.encode(dto.getNewPassword());
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
  public UserUnitedDetails getUserInfo(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    return getUserInfoDto(user);
  }

  @Override
  public UserUnitedDetails getUserInfoByToken(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userRepository.findByEmail(email)
      .orElseThrow(UserNotFoundException::new);

    return getUserInfoDto(user);
  }

  private UserUnitedDetails getUserInfoDto(User user) {
    long followerCount = followRepository.countByFollowingId(user);
    long followingCount = followRepository.countByFollowerId(user);
    long postCount = postRepository.countAllByUser(user);
    long likeCount = likeRepository.countAllByUser(user);
    UserDetails userDetails = UserDetails.from(user);

    return UserUnitedDetails.builder()
      .addAllFollowerCount(followerCount)
      .addAllFollowingCount(followingCount)
      .addAllGivenLikeCount(likeCount)
      .addAllPostCount(postCount)
      .addUserDetails(userDetails)
      .build();
  }

  private void checkDuplicatedInfo(UserUpdateRequest dto, User targetUser) {
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
