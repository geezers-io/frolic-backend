package com.frolic.sns.user.application;

import com.frolic.sns.post.repository.LikeDslRepository;
import com.frolic.sns.post.repository.PostRepository;
import com.frolic.sns.post.repository.LikeRepository;
import com.frolic.sns.auth.exception.AlreadyExistsUserException;
import com.frolic.sns.auth.exception.PasswordNotMatchException;
import com.frolic.sns.auth.application.security.JwtProvider;
import com.frolic.sns.user.application.info.UserName;
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
  private final LikeDslRepository likeDslRepository;
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

  public void updateUserPassword(User user, PasswordUpdateRequest updateRequest) {
    boolean isMatchPassword = passwordEncoder.matches(updateRequest.getPrevPassword(), user.getPassword());
    if (!isMatchPassword)
      throw new PasswordNotMatchException();

    String newPassword = passwordEncoder.encode(updateRequest.getNewPassword());
    user.changePassword(newPassword);
    userRepository.save(user);
  }

  public void deleteUser(User user, String password) {
    if (!passwordEncoder.matches(password, user.getPassword()))
      throw new PasswordNotMatchException();
    userRepository.delete(user);
  }

  public UserUnitedInfo getUserUnitedDetail(String username) {
    User user = userManager.getUser(new UserName(username));
    return userManager.getUserUnitedInfo(user);
  }

  public UserUnitedInfo getUserUnitedInfoByUser(User user) {
    return userManager.getUserUnitedInfo(user);
  }

  private void checkDuplicatedUserDetailWhenModified(UserUpdateRequest dto, User targetUser) {
    boolean isAlreadyExistsEmail = userRepository.existsByEmail(dto.getEmail());
    boolean isAlreadyExistsUsername = userRepository.existsByUsername(dto.getUsername());

    if (isAlreadyExistsEmail && !dto.getEmail().equals(targetUser.getEmail()))
      throw new AlreadyExistsUserException();

    if (isAlreadyExistsUsername && !dto.getUsername().equals(targetUser.getUsername()))
      throw new AlreadyExistsUserException();
  }

}
