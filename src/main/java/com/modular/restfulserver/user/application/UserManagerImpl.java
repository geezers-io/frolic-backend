package com.modular.restfulserver.user.application;

import com.modular.restfulserver.article.repository.ArticleRepository;
import com.modular.restfulserver.article.repository.LikeRepository;
import com.modular.restfulserver.auth.exception.AlreadyExistsUserException;
import com.modular.restfulserver.auth.exception.PasswordNotMatchException;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.dto.UserInfoDto;
import com.modular.restfulserver.user.dto.UserUpdateRequestDto;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.FollowRepository;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {

  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final ArticleRepository articleRepository;
  private final FollowRepository followRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtProvider jwtProvider;

  @Override
  public void updateUserInfo(String token, UserUpdateRequestDto dto) {
    User user = userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    ).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저이름입니다."));

    checkDuplicatedInfo(dto, user);

    String newPassword =  passwordEncoder.encode(dto.getPassword());
    user.changePassword(
      newPassword
    );
    user.changeEmail(dto.getEmail());
    user.changeUsername(dto.getUsername());
    userRepository.save(user);
  }

  @Override
  public void deleteUser(String token, String password) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저이름입니다."));
    if (!passwordEncoder.matches(password, user.getPassword()))
      throw new PasswordNotMatchException();
    userRepository.delete(user);
  }

  @Override
  public UserInfoDto getUserInfo(String username) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저이름입니다."));

    return getUserInfoDto(user);
  }

  @Override
  public UserInfoDto getUserInfoByToken(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저이름입니다."));

    return getUserInfoDto(user);
  }

  private UserInfoDto getUserInfoDto(User user) {
    long followerCount = followRepository.countByFollowingId(user);
    long followingCount = followRepository.countByFollowerId(user);
    long postCount = articleRepository.countAllByUser(user);
    long likeCount = likeRepository.countAllByUser(user);

    return UserInfoDto.builder()
      .addAllFollowerCount(followerCount)
      .addAllFollowingCount(followingCount)
      .addAllGivenLikeCount(likeCount)
      .addAllPostCount(postCount)
      .build();
  }

  private void checkDuplicatedInfo(UserUpdateRequestDto dto, User targetUser) {
    boolean isAlreadyExistsEmail = userRepository.existsByEmail(dto.getEmail());
    boolean isAlreadyExistsUsername = userRepository.existsByUsername(dto.getUsername());

    if (isAlreadyExistsEmail && !dto.getEmail().equals(targetUser.getEmail()))
      throw new AlreadyExistsUserException();

    if (isAlreadyExistsUsername && !dto.getUsername().equals(targetUser.getUsername()))
      throw new AlreadyExistsUserException();

  }

}
