package com.modular.restfulserver.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponseDto {
  public String accessToken;
  public String refreshToken;

  @Builder(setterPrefix = "add")
  public TokenResponseDto(
    String accessToken,
    String refreshToken
  ) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

}
