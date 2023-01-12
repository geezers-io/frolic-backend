package com.frolic.sns.auth.dto;

import com.frolic.sns.user.dto.UserUnitedInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginResponse {
  private final TokenInfo tokenInfo;
  private final UserUnitedInfo userUnitedInfo;

  @Builder(setterPrefix = "add")
  public UserLoginResponse(
    TokenInfo tokenInfo,
    UserUnitedInfo userUnitedInfo
  ) {
    this.tokenInfo = tokenInfo;
    this.userUnitedInfo = userUnitedInfo;
  }

  public static UserLoginResponse create(TokenInfo tokenInfo, UserUnitedInfo userUnitedInfo) {
    return UserLoginResponse.builder()
      .addTokenInfo(tokenInfo)
      .addUserUnitedInfo(userUnitedInfo)
      .build();
  }

}
