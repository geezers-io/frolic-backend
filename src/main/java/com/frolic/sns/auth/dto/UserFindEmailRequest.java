package com.frolic.sns.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserFindEmailRequest {
  @Pattern(
    regexp = "\\d{11}$",
    message = "폰 번호 형식이 잘못되었습니다."
  )
  private String phoneNumber;

  public UserFindEmailRequest(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}
