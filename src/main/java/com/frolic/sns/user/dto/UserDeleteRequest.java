package com.frolic.sns.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserDeleteRequest {

  @Pattern(
    regexp = "(^[!@#$%^&*])([A-Z]{1,1})([a-z0-9]{8,15})",
    message = "비밀번호 형식이 잘못되었습니다."
  )
  private String password;

}
