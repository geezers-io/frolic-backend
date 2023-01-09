package com.modular.restfulserver.auth.dto;

import com.modular.restfulserver.auth.util.AuthValidationMessages;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDetails {
  private final String accessToken;
  private final String refreshToken;

  @Builder(setterPrefix = "add")
  public TokenDetails(
    String accessToken,
    String refreshToken
  ) {
    Assert.hasText(accessToken, AuthValidationMessages.hasTextAccessToken);
    Assert.hasText(refreshToken, AuthValidationMessages.hasTextRefreshToken);

    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

}
