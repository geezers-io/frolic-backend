package com.frolic.sns.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class UserFindEmailRequest {

  @NotNull
  @NotBlank
  private String phoneNumber;

  public UserFindEmailRequest(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}
