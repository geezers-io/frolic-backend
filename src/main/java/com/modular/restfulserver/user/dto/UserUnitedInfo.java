package com.modular.restfulserver.user.dto;

import static com.modular.restfulserver.global.util.message.CommonMessageUtils.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class UserUnitedInfo {

  private final Long allPostCount;
  private final Long allFollowerCount;
  private final Long allFollowingCount;
  private final Long allGivenLikeCount;
  private final UserInfo userInfo;

  @Builder(setterPrefix = "add")
  public UserUnitedInfo(
    Long allPostCount,
    Long allFollowerCount,
    Long allFollowingCount,
    Long allGivenLikeCount,
    UserInfo userInfo
  ) {
    Assert.isInstanceOf(Long.class, allPostCount, getIllegalFieldError("allPostCount"));
    Assert.isInstanceOf(Long.class, allFollowerCount, getIllegalFieldError("allFollowerCount"));
    Assert.isInstanceOf(Long.class, allFollowingCount, getIllegalFieldError("allFollowingCount"));
    Assert.isInstanceOf(Long.class, allGivenLikeCount, getIllegalFieldError("allGivenLikeCount"));
    Assert.isInstanceOf(UserInfo.class, userInfo, getIllegalFieldError("userDetails"));

    this.allPostCount = allPostCount;
    this.allFollowerCount = allFollowerCount;
    this.allFollowingCount = allFollowingCount;
    this.allGivenLikeCount = allGivenLikeCount;
    this.userInfo = userInfo;
  }

}
