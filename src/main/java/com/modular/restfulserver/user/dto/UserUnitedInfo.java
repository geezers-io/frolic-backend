package com.modular.restfulserver.user.dto;

import static com.modular.restfulserver.global.util.message.FieldError.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class UserUnitedDetail {

  private final Long allPostCount;
  private final Long allFollowerCount;
  private final Long allFollowingCount;
  private final Long allGivenLikeCount;
  private final UserDetail userDetail;

  @Builder(setterPrefix = "add")
  public UserUnitedDetail(
    Long allPostCount,
    Long allFollowerCount,
    Long allFollowingCount,
    Long allGivenLikeCount,
    UserDetail userDetail
  ) {
    Assert.isInstanceOf(Long.class, allPostCount, getIllegalFieldError("allPostCount"));
    Assert.isInstanceOf(Long.class, allFollowerCount, getIllegalFieldError("allFollowerCount"));
    Assert.isInstanceOf(Long.class, allFollowingCount, getIllegalFieldError("allFollowingCount"));
    Assert.isInstanceOf(Long.class, allGivenLikeCount, getIllegalFieldError("allGivenLikeCount"));
    Assert.isInstanceOf(UserDetail.class, userDetail, getIllegalFieldError("userDetails"));

    this.allPostCount = allPostCount;
    this.allFollowerCount = allFollowerCount;
    this.allFollowingCount = allFollowingCount;
    this.allGivenLikeCount = allGivenLikeCount;
    this.userDetail = userDetail;
  }

}
