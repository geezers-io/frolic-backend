package com.frolic.sns.user.dto;

import lombok.Getter;

@Getter
public class FollowUserRequest {

  private final String username;

  private final String realname;


  public FollowUserRequest(String username, String realname) {
    this.username = username;
    this.realname = realname;
  }

}
