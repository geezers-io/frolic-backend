package com.frolic.sns.global.common.jwt;

import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @implNote json web token 내 정보로 Entity 를 손쉽게 가져올 수 있는 유틸입니다.
 */
@Component
@RequiredArgsConstructor
public class JwtEntityLoader {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  public User getUser(String token) {
    return userRepository.findByEmail(jwtProvider.getUserEmailByToken(token))
      .orElseThrow(UserNotFoundException::new);
  }

}
