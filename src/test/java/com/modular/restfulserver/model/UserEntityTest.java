package com.modular.restfulserver.model;

import com.modular.restfulserver.user.model.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserEntityTest {

  @Test
  public void User_email_비어있으면_exception() {
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
  public void User_username_비어있으면_exception() {
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
  public void User_password_비어있으면_exception() {
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
