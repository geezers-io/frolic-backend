package com.modular.restfulserver.user.dto;

import lombok.Getter;

@Getter
public class FollowUserDto {

  private final String username;

  private final String realname;


  public FollowUserDto(String username, String realname) {
    this.username = username;
    this.realname = realname;
  }

}
