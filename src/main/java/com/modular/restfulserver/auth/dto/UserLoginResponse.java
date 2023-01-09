package com.modular.restfulserver.auth.dto;

import com.modular.restfulserver.user.dto.UserUnitedDetails;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginResponse {
  private final TokenDetails tokenDetails;
  private final UserUnitedDetails userUnitedDetails;

  @Builder(setterPrefix = "add")
  public UserLoginResponse(
    TokenDetails tokenDetails,
    UserUnitedDetails userUnitedDetails
  ) {
    this.tokenDetails = tokenDetails;
    this.userUnitedDetails = userUnitedDetails;
  }

  public static UserLoginResponse create(TokenDetails tokenDetails, UserUnitedDetails userUnitedDetails) {
    return UserLoginResponse.builder()
      .addTokenDetails(tokenDetails)
      .addUserUnitedDetails(userUnitedDetails)
      .build();
  }

}
