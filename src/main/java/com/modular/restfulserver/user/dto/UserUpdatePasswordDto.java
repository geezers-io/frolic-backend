package com.modular.restfulserver.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class UserUpdatePasswordDto {

  @NotNull
  @Pattern(
    regexp = "/^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$/",
    message = "비밀번호 형식이 잘못되었습니다."
  )
  private final String prevPassword;

  @NotNull
  @Pattern(
    regexp = "/^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$/",
    message = "비밀번호 형식이 잘못되었습니다."
  )
  private final String newPassword;

  public UserUpdatePasswordDto(String prevPassword, String newPassword) {
    this.prevPassword = prevPassword;
    this.newPassword = newPassword;
  }

}
