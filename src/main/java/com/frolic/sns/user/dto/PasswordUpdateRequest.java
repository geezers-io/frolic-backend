package com.frolic.sns.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class PasswordUpdateRequest {

  @NotNull
  @Pattern(
    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$",
    message = "비밀번호 형식이 잘못되었습니다."
  )
  private String prevPassword;

  @NotNull
  @Pattern(
    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$",
    message = "비밀번호 형식이 잘못되었습니다."
  )
  private String newPassword;

  public PasswordUpdateRequest(String prevPassword, String newPassword) {
    this.prevPassword = prevPassword;
    this.newPassword = newPassword;
  }

}
