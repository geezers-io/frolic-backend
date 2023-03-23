package com.frolic.sns.auth.application.security;

import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.application.info.UserEmail;
import com.frolic.sns.user.model.User;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FrolicAuthProvider implements AuthenticationProvider {

  private final UserManager userManager;
  private final JwtProvider jwtProvider;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String credentials =  (String) authentication.getCredentials();
    Claims claims = jwtProvider.parseClaims(credentials);
    String userEmail = (String) claims.get(TokenKey.USER_EMAIL.name());
    User user = userManager.getUser(new UserEmail(userEmail));
    return new FrolicAuthenticationToken(user.getEmail(), credentials);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isInstance(Authentication.class);
  }

}
