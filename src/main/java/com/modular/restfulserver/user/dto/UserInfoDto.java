package com.modular.restfulserver.user.dto;

import com.modular.restfulserver.global.exception.BuilderArgumentNotValidException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class UserInfoDto {

  private final Long allPostCount;
  private final Long allFollowerCount;
  private final Long allFollowingCount;
  private final Long allGivenLikeCount;
  private final UserInfoForClientDto userInfo;

  @Builder(setterPrefix = "add")
  public UserInfoDto(
    Long allPostCount,
    Long allFollowerCount,
    Long allFollowingCount,
    Long allGivenLikeCount,
    UserInfoForClientDto userInfo
  ) {
    Assert.isInstanceOf(Long.class, allPostCount, "allPostCount field must be long");
    Assert.isInstanceOf(Long.class, allFollowerCount, "allFollowerCount field must be long");
    Assert.isInstanceOf(Long.class, allFollowingCount, "allFollowingCount field must be long");
    Assert.isInstanceOf(Long.class, allGivenLikeCount, "allGivenLikeCount field must be long");
    if (userInfo == null)
      throw new BuilderArgumentNotValidException("[UserInfoDto] userInfo must not null");

    this.allPostCount = allPostCount;
    this.allFollowerCount = allFollowerCount;
    this.allFollowingCount = allFollowingCount;
    this.allGivenLikeCount = allGivenLikeCount;
    this.userInfo = userInfo;
  }

}
