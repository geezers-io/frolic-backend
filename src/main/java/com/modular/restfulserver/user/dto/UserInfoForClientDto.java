package com.modular.restfulserver.user.dto;

import static com.modular.restfulserver.global.utils.message.FieldError.*;
import com.modular.restfulserver.user.model.User;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserInfoForClientDto {

  private final Long userId;
  private final String email;
  private final String username;

  private final String realname;
  private final LocalDateTime createdDate;
  private final LocalDateTime updatedDate;

  @Builder(setterPrefix = "add")
  public UserInfoForClientDto(Long userId, String email, String username, String realname, LocalDateTime createdDate, LocalDateTime updatedDate) {
    Assert.isInstanceOf(Long.class, userId, getIllegalFieldError("userId"));
    Assert.isInstanceOf(String.class, email, getIllegalFieldError("email"));
    Assert.isInstanceOf(String.class, username, getIllegalFieldError("username"));
    Assert.isInstanceOf(String.class, realname, getIllegalFieldError("realname"));
    Assert.isInstanceOf(LocalDateTime.class, createdDate, getIllegalFieldError("createdDate"));
    Assert.isInstanceOf(LocalDateTime.class, updatedDate, getIllegalFieldError("updatedDate"));

    this.userId = userId;
    this.email = email;
    this.username = username;
    this.realname = realname;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

  public static UserInfoForClientDto from(User user) {
    return UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .addRealname(user.getRealname())
      .addCreatedDate(user.getCreatedDate())
      .addUpdatedDate(user.getUpdatedDate())
      .build();
  }

}
