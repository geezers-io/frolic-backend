package com.frolic.sns.user.application;

import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserDslRepository;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public final class UserManager {
  private final UserRepository repository;

  private final UserDslRepository dslRepository;

  private final JwtProvider jwtProvider;


  public User getUserByHttpRequest(HttpServletRequest request) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    String email = jwtProvider.getUserEmailByToken(token);
    return dslRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  public User getUserByusername(String username) {
    return dslRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
  }

}
