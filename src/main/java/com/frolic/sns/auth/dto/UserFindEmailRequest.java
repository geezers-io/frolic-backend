package com.frolic.sns.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFindEmailRequest {

  // TODO: validaiton
  private String phoneNumber;
  public UserFindEmailRequest(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}
