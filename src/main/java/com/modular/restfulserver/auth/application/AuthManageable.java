package com.modular.restfulserver.auth.application;

import com.modular.restfulserver.auth.dto.TokenInfo;
import com.modular.restfulserver.auth.dto.UserLoginRequest;
import com.modular.restfulserver.auth.dto.UserSignupRequest;

import java.util.Map;

public interface AuthManageable {
  TokenInfo saveUser(UserSignupRequest userSignupRequest);

  TokenInfo loginUser(UserLoginRequest userLoginRequest);

  Map<String, String> refresh(String refreshToken);

  void logout(String token);

}
