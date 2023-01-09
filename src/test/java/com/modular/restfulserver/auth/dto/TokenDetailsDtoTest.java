package com.modular.restfulserver.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.MockData;

import static org.junit.jupiter.api.Assertions.*;

class TokenDetailsDtoTest {

  @Test
  @DisplayName("accessToken 이 없으면 예외가 발생한다.")
  void hasTextAccessToken() {
    assertThrows(IllegalArgumentException.class, () -> TokenDetail.builder()
      .addRefreshToken("refreshToken")
      .addUserInfo(MockData.mockUserDetailsForClientDto)
      .build());
  }

  @Test
  @DisplayName("refreshToken 이 없으면 예외가 발생한다.")
  void hasTextRefreshToken() {
    assertThrows(IllegalArgumentException.class, () -> TokenDetail.builder()
      .addAccessToken("accessToken")
      .addUserInfo(MockData.mockUserDetailsForClientDto)
      .build());
  }

  @Test
  @DisplayName("userInfo 가 없으면 예외가 발생한다.")
  void instanceOfUserInfo() {
    assertThrows(IllegalArgumentException.class, () -> TokenDetail.builder()
      .addAccessToken("accessToken")
      .addRefreshToken("refreshToken")
      .build());
  }

}
