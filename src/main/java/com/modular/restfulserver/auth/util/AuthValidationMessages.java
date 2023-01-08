package com.modular.restfulserver.auth.util;

import com.modular.restfulserver.global.util.message.ValidationMessage;

public class AuthValidationMessages implements ValidationMessage {
  public final static String hasTextAccessToken = "accessToken" + mustBeNotNullMessage;
  public final static String hasTextRefreshToken = "refreshToken" + mustBeNotNullMessage;

  public final static String hashTextEmail = "email" + mustBeNotNullMessage;
  public final static String hasTextPassword = "password" + mustBeNotNullMessage;


}
