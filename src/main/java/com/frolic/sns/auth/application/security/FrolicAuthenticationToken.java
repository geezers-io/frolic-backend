package com.frolic.sns.auth.application.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class FrolicAuthenticationToken extends AbstractAuthenticationToken {

  private final String principal; // email 값을 전달받습니다.
  private final String credentials; // Access Token 값을 전달받습니다.

  public FrolicAuthenticationToken(String email, String token) {
    super(null);
    this.principal = email;
    this.credentials = token;
    setAuthenticated(false);
  }

}
