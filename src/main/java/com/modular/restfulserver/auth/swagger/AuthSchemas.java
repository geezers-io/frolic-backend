package com.modular.restfulserver.auth.swagger;

import com.modular.restfulserver.auth.dto.UserLoginResponse;
import lombok.Getter;


public class AuthSchemas {

  @Getter
  public static class Signup {
    UserLoginResponse data;
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
