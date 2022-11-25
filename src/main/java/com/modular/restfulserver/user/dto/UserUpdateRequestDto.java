package com.modular.restfulserver.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor
public class UserUpdateRequestDto {

  @Email(message = "이메일 형식이 잘못되었습니다.")
  private String email;

  @Pattern(
    regexp = "[^!@#$%^&]([a-zA-Z가-힣0-9]){3,10}",
    message = "사용자 이름 형식이 잘못되었습니다."
  )
  private String username;

}
