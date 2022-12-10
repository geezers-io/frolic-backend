package com.modular.restfulserver.auth.dto;

import com.modular.restfulserver.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

  @Email
  private String email;

  @Pattern(
    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$",
    message = "비밀번호 형식이 잘못되었습니다."
  )
  private String password;


  public User toEntity() {
    return User.builder()
      .addEmail(email)
      .addPassword(password)
      .build();
  }

  @Builder(setterPrefix = "add")
  public UserLoginRequestDto(String email, String password, String username) {
    Assert.hasText(email, "[UserLoginRequestDto] email must be not empty");
    Assert.hasText(password, "[UserLoginRequestDto] password must be not empty");

    this.email = email;
    this.password = password;
  }

  // TODO: 2022-11-24 validation
  public void setEncodePassword(String encodePassword) {
    this.password = encodePassword;
  }

  @Override
  public String toString() {
    return "UserLoginRequestDto{" +
      "email='" + email + '\'' +
      ", password='" + password + '\'' +
      '}';
  }

}
