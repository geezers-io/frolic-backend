package com.frolic.sns.global.common.jwt;

import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @deprecated 직접적으로 User 를 관리하는 객체로 기능이 대체되었음 ( UserManager.class )
 */
@Deprecated
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
