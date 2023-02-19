package com.frolic.sns.auth.dto;

import com.frolic.sns.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserTempPasswordRequest {

  @NotNull
  @Email(message = "이메일 형식이 잘못되었습니다.")
  private String email;
  @Pattern(
    regexp = "\\d{11}$",
    message = "폰 번호 형식이 잘못되었습니다."
  )
  private String phoneNumber;

  public UserTempPasswordRequest(String email, String phoneNumber) {
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    return obj != null && getClass() == obj.getClass();
  }

}
