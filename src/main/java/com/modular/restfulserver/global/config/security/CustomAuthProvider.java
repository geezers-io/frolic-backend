package com.modular.restfulserver.global.config.security;

import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

  private final UserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = authentication.getName();
    User user = userRepository.findByEmail(email)
        .orElseThrow(
          () -> new UsernameNotFoundException("존재하지 않는 이메일 사용자입니다.")
        );
    return new CustomEmailPasswordAuthToken(user.getEmail());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }

}
