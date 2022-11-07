package com.modular.restfulserver.global.config.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class CustomEmailPasswordAuthToken extends AbstractAuthenticationToken {

  private final String principal;
  private final String credentials = null;

  public CustomEmailPasswordAuthToken(String principal) {
    super(null);
    this.principal = principal;
    setAuthenticated(false);
  }
}
