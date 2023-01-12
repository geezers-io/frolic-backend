package com.frolic.sns.auth.dto;

import static com.frolic.sns.global.util.message.CommonMessageUtils.*;
import com.frolic.sns.user.model.User;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserSignupRequest {

  @NotNull
  @Email(message = "이메일 형식이 잘못되었습니다.")
  private String email;

  // TODO: 2022-11-25 regexp 구문 파악하기 
  @NotNull
  @Pattern(
    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$",
    message = "비밀번호 형식이 잘못되었습니다."
  )
  private String password;

  @NotNull
  @Pattern(
    regexp = "^(?=.*[a-zA-Z0-9])(?=.*_?)[a-zA-Z0-9_]{4,15}$",
    message = "사용자 이름 형식이 잘못되었습니다."
  )
  private String username;

  @NotNull
  @Pattern(
    regexp = "^(?=.[가-힣ㄱ-ㅎa-zA-Z])[가-힣ㄱ-ㅎa-zA-Z]{1,12}$",
    message = "실명 형식이 잘못되었습니다."
  )
  private String realname;

  @NotNull
  @Pattern(
    regexp = "\\d{3}\\-\\d{3,4}\\-\\d{4}$",
    message = "폰 번호 형식이 잘못되었습니다."
  )
  private String phoneNumber;

  @Builder(setterPrefix = "add")
  public UserSignupRequest(
    String email,
    String password,
    String username,
    String realname,
    String phoneNumber
  ) {
    Assert.hasText(email, getIllegalFieldError("email"));
    Assert.hasText(password, getIllegalFieldError("password"));
    Assert.hasText(username, getIllegalFieldError("username"));
    Assert.hasText(realname, getIllegalFieldError("realname"));
    Assert.hasText(phoneNumber, getIllegalFieldError("phoneNumber"));

    this.email = email;
    this.password = password;
    this.username = username;
    this.realname = realname;
    this.phoneNumber = phoneNumber;
  }

  public User toEntity() {
    return User.builder()
      .addEmail(email)
      .addUsername(username)
      .addRealname(realname)
      .addPassword(password)
      .addPhoneNumber(phoneNumber)
      .build();
  }

  public void setEncodePassword(String encodePassword) {
    this.password = encodePassword;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
