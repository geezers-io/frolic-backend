package com.frolic.sns.auth.dto;

import com.frolic.sns.auth.util.AuthValidationMessages;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenInfo {
  private final String accessToken;
  private final String refreshToken;

  @Builder(setterPrefix = "add")
  public TokenInfo(
    String accessToken,
    String refreshToken
  ) {
    Assert.hasText(accessToken, AuthValidationMessages.hasTextAccessToken);
    Assert.hasText(refreshToken, AuthValidationMessages.hasTextRefreshToken);

    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

}
