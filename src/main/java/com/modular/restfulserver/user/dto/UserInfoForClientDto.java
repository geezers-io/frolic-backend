package com.modular.restfulserver.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoForClientDto {

  private final Long userId;
  private final String email;
  private final String username;

  @Builder(setterPrefix = "add")
  public UserInfoForClientDto(Long userId, String email, String username) {
    this.userId = userId;
    this.email = email;
    this.username = username;
  }

}
