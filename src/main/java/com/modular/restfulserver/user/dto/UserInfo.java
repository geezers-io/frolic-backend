package com.modular.restfulserver.user.dto;

import static com.modular.restfulserver.global.util.message.CommonMessageUtils.*;
import com.modular.restfulserver.user.model.User;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserInfo {

  private final Long id;
  private final String email;
  private final String username;

  private final String realname;
  private final String phoneNumber;
  private final LocalDateTime createdDate;
  private final LocalDateTime updatedDate;

  @Builder(setterPrefix = "add")
  public UserInfo(
    Long id,
    String email,
    String username,
    String realname,
    String phoneNumber,
    LocalDateTime createdDate,
    LocalDateTime updatedDate
  ) {
    Assert.isInstanceOf(Long.class, id, getIllegalFieldError("userId"));
    Assert.isInstanceOf(LocalDateTime.class, createdDate, getIllegalFieldError("createdDate"));
    Assert.isInstanceOf(LocalDateTime.class, updatedDate, getIllegalFieldError("updatedDate"));
    Assert.hasText(email, getIllegalFieldError("email"));
    Assert.hasText(username, getIllegalFieldError("username"));
    Assert.hasText(realname, getIllegalFieldError("realname"));
    Assert.hasText(phoneNumber, getIllegalFieldError("phoneNumber"));

    this.id = id;
    this.email = email;
    this.username = username;
    this.realname = realname;
    this.phoneNumber = phoneNumber;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

  public static UserInfo from(User user) {
    return UserInfo.builder()
      .addId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .addRealname(user.getRealname())
      .addPhoneNumber(user.getPhoneNumber())
      .addCreatedDate(user.getCreatedDate())
      .addUpdatedDate(user.getUpdatedDate())
      .build();
  }

}
