package com.modular.restfulserver.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.MockData;

import static org.junit.jupiter.api.Assertions.*;

class TokenInfoDtoTest {

  @Test
  @DisplayName("accessToken 이 없으면 예외가 발생한다.")
  void hasTextAccessToken() {
    assertThrows(IllegalArgumentException.class, () -> TokenInfo.builder()
      .addRefreshToken("refreshToken")
      .addUserInfo(MockData.mockUserInfoForClientDto)
      .build());
  }

  @Test
  @DisplayName("refreshToken 이 없으면 예외가 발생한다.")
  void hasTextRefreshToken() {
    assertThrows(IllegalArgumentException.class, () -> TokenInfo.builder()
      .addAccessToken("accessToken")
      .addUserInfo(MockData.mockUserInfoForClientDto)
      .build());
  }

  @Test
  @DisplayName("userInfo 가 없으면 예외가 발생한다.")
  void instanceOfUserInfo() {
    assertThrows(IllegalArgumentException.class, () -> TokenInfo.builder()
      .addAccessToken("accessToken")
      .addRefreshToken("refreshToken")
      .build());
  }

}
