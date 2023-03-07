package com.frolic.sns.user.application;

import com.frolic.sns.post.repository.PostRepository;
import com.frolic.sns.post.repository.LikeRepository;
import com.frolic.sns.auth.exception.AlreadyExistsUserException;
import com.frolic.sns.auth.exception.PasswordNotMatchException;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.user.dto.UserUnitedInfo;
import com.frolic.sns.user.dto.UserInfo;
import com.frolic.sns.user.dto.PasswordUpdateRequest;
import com.frolic.sns.user.dto.UserUpdateRequest;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.FollowRepository;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final FollowRepository followRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtProvider jwtProvider;
  private final UserManager userManager;

  public UserInfo updateUserDetail(User user, UserUpdateRequest userUpdateRequest) {
    checkDuplicatedUserDetailWhenModified(userUpdateRequest, user);
    user.changeEmail(userUpdateRequest.getEmail());
    user.changeUsername(userUpdateRequest.getUsername());
    user.changeRealname(userUpdateRequest.getRealname());
    user.changePhoneNumber(userUpdateRequest.getPhoneNumber());
    userRepository.save(user);

    return UserInfo.from(user);
  }

  public void updateUserPassword(String token, PasswordUpdateRequest updateRequest) {
    User user = getUserByToken(token);
    boolean isMatchPassword = passwordEncoder.matches(updateRequest.getPrevPassword(), user.getPassword());
    if (!isMatchPassword)
      throw new PasswordNotMatchException();

    String newPassword = passwordEncoder.encode(updateRequest.getNewPassword());
    user.changePassword(newPassword);
    userRepository.save(user);
  }

  public void deleteUser(String token, String password) {
    User user = getUserByToken(token);
    if (!passwordEncoder.matches(password, user.getPassword()))
      throw new PasswordNotMatchException();
    userRepository.delete(user);
  }

  public UserUnitedInfo getUserUnitedDetail(String username) {
    User user = userManager.getUserByusername(username);
    return buildUserUnitedDetail(user);
  }

  public UserUnitedInfo getUserUnitedInfoByUser(User user) {
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
