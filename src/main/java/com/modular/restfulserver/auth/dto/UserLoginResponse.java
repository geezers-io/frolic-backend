package com.modular.restfulserver.auth.dto;

import com.modular.restfulserver.user.dto.UserUnitedDetails;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginResponse {
  private final TokenDetail tokenDetail;
  private final UserUnitedDetails userUnitedDetails;

  @Builder(setterPrefix = "add")
  public UserLoginResponse(
    TokenDetail tokenDetail,
    UserUnitedDetails userUnitedDetails
  ) {
    this.tokenDetail = tokenDetail;
    this.userUnitedDetails = userUnitedDetails;
  }

  public static UserLoginResponse create(TokenDetail tokenDetail, UserUnitedDetails userUnitedDetails) {
    return UserLoginResponse.builder()
      .addTokenDetails(tokenDetail)
      .addUserUnitedDetails(userUnitedDetails)
      .build();
  }

}
