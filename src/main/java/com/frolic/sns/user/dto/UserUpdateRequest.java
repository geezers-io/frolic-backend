package com.frolic.sns.user.dto;

import com.frolic.sns.global.util.message.CommonMessageUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
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

  @Builder(setterPrefix = "add")
  public UserUpdateRequest(
    String email,
    String username,
    String realname,
    String phoneNumber
  ) {
    Assert.hasText(email, CommonMessageUtils.getIllegalFieldError("email"));
    Assert.hasText(username, CommonMessageUtils.getIllegalFieldError("username"));
    Assert.hasText(realname, CommonMessageUtils.getIllegalFieldError("realname"));
    Assert.hasText(phoneNumber, CommonMessageUtils.getIllegalFieldError("phoneNumber"));

    this.email = email;
    this.username = username;
    this.realname = realname;
    this.phoneNumber = phoneNumber;
  }

}
