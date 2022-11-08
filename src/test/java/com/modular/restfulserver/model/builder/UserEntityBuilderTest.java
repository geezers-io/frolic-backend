package com.modular.restfulserver.model.builder;

import com.modular.restfulserver.user.model.User;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserEntityBuilderTest {

  @Test
  @DisplayName("email 필드가 비어있으면 예외처리 된다.")
  public void User_email_empty_exception() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> {
      User.builder()
        .addEmail("")
        .addUsername("username")
        .addPassword("password")
        .build();
    });
    assertTrue(ex.getMessage().contains("email must not be null"));
  }

  @Test
  @DisplayName("username 필드가 비어있으면 예외처리 된다.")
  public void User_username_empty_exception() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> {
      User.builder()
        .addEmail("email")
        .addUsername("")
        .addPassword("password")
        .build();
    });
    assertTrue(ex.getMessage().contains("username must not be null"));
  }

  @Test
  @DisplayName("password 필드가 비어있으면 예외처리 된다.")
  public void User_password_empty_exception() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> {
      User.builder()
        .addEmail("email")
        .addUsername("username")
        .addPassword("")
        .build();
    });
    assertTrue(ex.getMessage().contains("password must not be null"));
  }

}
