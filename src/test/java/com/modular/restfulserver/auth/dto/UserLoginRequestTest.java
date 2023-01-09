package com.modular.restfulserver.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginRequestTest {

  @Test
  @DisplayName("email 값이 없으면 예외가 발생한다.")
  void hashTextEmail() {
    assertThrows(IllegalArgumentException.class, () -> UserLoginRequest.builder()
      .addPassword("password")
      .build()
    );
  }

  @Test
  @DisplayName("password 값이 없으면 예외가 발생한다.")
  void hashTextPassword() {
    assertThrows(IllegalArgumentException.class, () -> UserLoginRequest.builder()
      .addEmail("galaxy4276@gamil.com")
      .build()
    );
  }

  @Test
  @DisplayName("패스워드가 정규식 검사를 통과하지 못하면 예외가 발생한다.")
  void initFaultPassword() {
    assertThrows(IllegalArgumentException.class, () -> {
      UserLoginRequest.builder()
        .addEmail("galaxy4276@gmail.com")
        .addPassword("zzz")
        .build();
    });
  }

}
