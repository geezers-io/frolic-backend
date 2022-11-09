package com.modular.restfulserver.user.application;

import com.modular.restfulserver.auth.exception.InvalidTokenException;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.dto.UserInfoDto;
import com.modular.restfulserver.user.dto.UserUpdateRequestDto;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.FollowRepository;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {

  private final UserRepository userRepository;
  private final FollowRepository followRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtProvider jwtProvider;

  @Override
  public void updateUserInfo(String token, UserUpdateRequestDto dto) {
    Optional<User> userByToken = userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    );
    User user = userRepository.findByEmail(dto.getEmail())
      .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저이름입니다."));
    if (
      userByToken.isPresent()
      && !Objects.equals(userByToken.get().getId(), user.getId())
    )
      throw new InvalidTokenException();

    String newPassword = passwordEncoder.encode(dto.getPassword());
    user.changePassword(newPassword);
    user.changeEmail(dto.getEmail());
    user.changeUsername(dto.getUsername());
  }

  @Override
  public void deleteUser(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저이름입니다."));
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
    long followerCount = followRepository.countByFollowerId(user);
    long followingCount = followRepository.countByFollowingId(user);

    return UserInfoDto.builder()
      .addAllFollowerCount(followerCount)
      .addAllFollowingCount(followingCount)
      .addAllGivenLikeCount(0L)
      .addAllPostCount(0L)
      .build();
  }
}
