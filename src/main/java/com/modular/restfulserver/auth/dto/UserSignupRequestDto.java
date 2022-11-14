package com.modular.restfulserver.auth.dto;

import com.modular.restfulserver.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {

  @NotNull
  @Email(message = "이메일 형식이 잘못되었습니다.")
  private String email;

  @NotNull
  @Pattern(
    regexp = "(^[!@#$%^&*])([A-Z]{1,1})([a-z0-9]{8,15})",
    message = "비밀번호 형식이 잘못되었습니다."
  )
  private String password;

  @NotNull
  @Pattern(
    regexp = "[^!@#$%^&]([a-zA-Z가-힣0-9]){3,10}",
    message = "사용자 이름 형식이 잘못되었습니다."
  )
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
