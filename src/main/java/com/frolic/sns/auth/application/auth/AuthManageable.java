package com.frolic.sns.auth.application.auth;

import com.frolic.sns.auth.dto.UserLoginRequest;
import com.frolic.sns.auth.dto.UserLoginResponse;
import com.frolic.sns.auth.dto.UserSignupRequest;

import java.util.Map;

public interface AuthManageable {
  UserLoginResponse saveUser(UserSignupRequest userSignupRequest);

  UserLoginResponse loginUser(UserLoginRequest userLoginRequest);

  Map<String, String> refresh(String refreshToken);

  void logout(String token);

}
