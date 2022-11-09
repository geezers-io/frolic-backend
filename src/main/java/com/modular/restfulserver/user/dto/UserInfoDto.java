package com.modular.restfulserver.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class UserInfoDto {

  private final Long allPostCount;
  private final Long allFollowerCount;
  private final Long allFollowingCount;
  private final Long allGivenLikeCount;

  @Builder(setterPrefix = "add")
  public UserInfoDto(
    Long allPostCount,
    Long allFollowerCount,
    Long allFollowingCount,
    Long allGivenLikeCount
  ) {
    Assert.isInstanceOf(Long.class, allPostCount, "allPostCount field must be long");
    Assert.isInstanceOf(Long.class, allFollowerCount, "allFollowerCount field must be long");
    Assert.isInstanceOf(Long.class, allFollowingCount, "allFollowingCount field must be long");
    Assert.isInstanceOf(Long.class, allGivenLikeCount, "allGivenLikeCount field must be long");

    this.allPostCount = allPostCount;
    this.allFollowerCount = allFollowerCount;
    this.allFollowingCount = allFollowingCount;
    this.allGivenLikeCount = allGivenLikeCount;
  }

}
