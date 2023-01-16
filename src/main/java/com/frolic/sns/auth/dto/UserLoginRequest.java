package com.frolic.sns.auth.dto;

import com.frolic.sns.auth.util.AuthConstant;
import com.frolic.sns.auth.util.AuthValidationMessages;
import com.frolic.sns.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import javax.validation.constraints.*;
import static java.util.regex.Pattern.*;

@Getter
@NoArgsConstructor
public class UserLoginRequest {

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
  public UserLoginRequest(String email, String password) {
    Assert.hasText(email, AuthValidationMessages.hashTextEmail);
    Assert.hasText(password, AuthValidationMessages.hasTextPassword);
    validPassword(password);

    this.email = email;
    this.password = password;
  }

  public void setEncodePassword(String encodePassword) {
    this.password = encodePassword;
  }

  private void validPassword(String password) {
    if (!matches(AuthConstant.passwordRegex, password))
      throw new IllegalArgumentException();
  }

  @Override
  public String toString() {
    return "UserLoginRequestDto{" +
      "email='" + email + '\'' +
      ", password='" + password + '\'' +
      '}';
  }

}
