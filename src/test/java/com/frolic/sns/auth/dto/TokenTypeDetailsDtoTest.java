package com.frolic.sns.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTypeDetailsDtoTest {

  @Test
  @DisplayName("accessToken 이 없으면 예외가 발생한다.")
  void hasTextAccessToken() {
    assertThrows(IllegalArgumentException.class, () -> TokenInfo.builder()
      .addRefreshToken("refreshToken")
      .build());
  }

  @Test
  @DisplayName("refreshToken 이 없으면 예외가 발생한다.")
  void hasTextRefreshToken() {
    assertThrows(IllegalArgumentException.class, () -> TokenInfo.builder()
      .addAccessToken("accessToken")
      .build());
  }

}
