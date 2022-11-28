package com.modular.restfulserver.user.dto;

import com.modular.restfulserver.user.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoForClientDto {

  private final Long userId;
  private final String email;
  private final String username;

  private final String realname;

  @Builder(setterPrefix = "add")
  public UserInfoForClientDto(Long userId, String email, String username, String realname) {

    this.userId = userId;
    this.email = email;
    this.username = username;
    this.realname = realname;
  }

  public static UserInfoForClientDto from(User user) {
    return UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .addRealname(user.getRealname())
      .build();
  }

}
