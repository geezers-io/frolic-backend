package com.modular.restfulserver.auth.dto;

import com.modular.restfulserver.auth.util.AuthValidationMessages;
import com.modular.restfulserver.user.dto.UserInfo;
import com.modular.restfulserver.user.util.UserValidationMessages;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponseDto {
  private final String accessToken;
  private final String refreshToken;
  private final UserInfo userInfo;

  @Builder(setterPrefix = "add")
  public TokenResponseDto(
    String accessToken,
    String refreshToken,
    UserInfo userInfo
  ) {
    Assert.hasText(accessToken, AuthValidationMessages.hasTextAccessToken);
    Assert.hasText(refreshToken, AuthValidationMessages.hasTextRefreshToekn);
    Assert.isInstanceOf(UserInfo.class, userInfo, UserValidationMessages.isInstanceOfUserInfo);

    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.userInfo = userInfo;
  }

}
