package com.frolic.sns.auth.swagger;

import com.frolic.sns.auth.dto.UserLoginResponse;
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

  @Getter
  public static class AccessToken {
    @Getter
    static class RefreshTokenDetails {
      String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJVU0VSX0VNQUlMIjoiZ2FsYXh5aGk0Mjc2QGdtYWlsLmNvbSIsIlRPS0VOX1RZUEUiOiJBQ0NFU1NfVE9LRU4iLCJpYXQiOjE2ODAwMDI4MDgsImV4cCI6MTY4MDAwMzQwOH0.QBkf0GNMnD6p";
    }
    RefreshTokenDetails data;
  }

}
