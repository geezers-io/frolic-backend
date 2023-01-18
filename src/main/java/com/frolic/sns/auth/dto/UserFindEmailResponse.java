package com.frolic.sns.auth.dto;


import lombok.Getter;

@Getter
public class UserFindEmailResponse {

  private final String email;

  public UserFindEmailResponse(String email) {
    this.email = email;
  }

}
