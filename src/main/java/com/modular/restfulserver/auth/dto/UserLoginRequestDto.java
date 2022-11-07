package com.modular.restfulserver.auth.dto;

import com.modular.restfulserver.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {

  @Email
  private String email;

  @Pattern(regexp = "(^[!@#$%^&*])([A-Z]{1,1})([a-z0-9]{8,15})")
  private String password;

  @Pattern(regexp = "[^!@#$%^&]([a-zA-Z가-힣0-9]){3,10}")
  private String username;

  public User toEntity() {
    return User.builder()
      .addEmail(email)
      .addUsername(username)
      .addPassword(password)
      .build();
  }

  public void setEncodePassword(String encodePassword) {
    this.password = encodePassword;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
