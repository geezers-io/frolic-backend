package com.modular.restfulserver.auth.dto;

import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponseDto {
  private final String accessToken;
  private final String refreshToken;
  private final UserInfoForClientDto userInfo;

  @Builder(setterPrefix = "add")
  public TokenResponseDto(
    String accessToken,
    String refreshToken,
    UserInfoForClientDto userInfo
  ) {
    Assert.hasText(accessToken, "[TokenResponseDto] accessToken must not empty");
    Assert.hasText(refreshToken, "[TokenResponseDto] refreshToken must not empty");

    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.userInfo = userInfo;
  }

}
