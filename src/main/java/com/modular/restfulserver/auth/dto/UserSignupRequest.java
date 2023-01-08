package com.modular.restfulserver.auth.dto;

import static com.modular.restfulserver.global.util.message.FieldError.*;
import com.modular.restfulserver.user.model.User;
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

  @Builder(setterPrefix = "add")
  public UserSignupRequest(String email, String password, String username, String realname) {
    Assert.isInstanceOf(String.class, email, getIllegalFieldError("email"));
    Assert.isInstanceOf(String.class, password, getIllegalFieldError("password"));
    Assert.isInstanceOf(String.class, username, getIllegalFieldError("username"));
    Assert.isInstanceOf(String.class, realname, getIllegalFieldError("realname"));

    this.email = email;
    this.password = password;
    this.username = username;
    this.realname = realname;
  }

  public User toEntity() {
    return User.builder()
      .addEmail(email)
      .addUsername(username)
      .addRealname(realname)
      .addPassword(password)
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
