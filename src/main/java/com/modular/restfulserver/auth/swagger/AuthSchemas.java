package com.modular.restfulserver.auth.swagger;

import com.modular.restfulserver.auth.dto.TokenResponseDto;
import com.modular.restfulserver.user.dto.UserInfo;
import lombok.Getter;

import java.time.LocalDateTime;

public class AuthSchemas {

  @Getter
  public static class Signup {
    TokenResponseDto data = TokenResponseDto.builder()
      .addAccessToken("eyAccessToken")
      .addUserInfo(UserInfo.builder()
        .addId(627L)
        .addUsername("galaxy4276")
        .addRealname("은기최")
        .addEmail("galaxyhi4276@gmail.com")
        .addCreatedDate(LocalDateTime.now())
        .addUpdatedDate(LocalDateTime.now())
        .build()
      )
      .addRefreshToken("eyRefreshed")
      .build();
  }

  @Getter
  public static class ReissueToken {

    @Getter
    static class RefreshTokenData {
      String refreshToken = "string";
    }

    RefreshTokenData data;
  }

}
