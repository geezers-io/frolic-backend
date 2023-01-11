package com.modular.restfulserver.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class UserUpdateRequest {

  @Email(message = "이메일 형식이 잘못되었습니다.")
  private String email;

  @Pattern(
    regexp = "[^!@#$%^&]([a-zA-Z가-힣0-9]){3,10}",
    message = "사용자 이름 형식이 잘못되었습니다."
  )
  private String username;

  @Pattern(
    regexp = "^(?=.[가-힣ㄱ-ㅎa-zA-Z])[가-힣ㄱ-ㅎa-zA-Z]{1,12}$",
    message = "실명 형식이 잘못되었습니다."
  )
  private String realname;

  @Pattern(
    regexp = "\\d{3}\\-\\d{3,4}\\-\\d{4}$",
    message = "폰 번호 형식이 잘못되었습니다."
  )
  private String phoneNumber;

}
