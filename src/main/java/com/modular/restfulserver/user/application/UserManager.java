package com.modular.restfulserver.user.application;

import com.modular.restfulserver.user.dto.UserIntegrationInfo;
import com.modular.restfulserver.user.dto.UserInfo;
import com.modular.restfulserver.user.dto.PasswordUpdateRequest;
import com.modular.restfulserver.user.dto.UserUpdateRequest;

public interface UserManager {

  UserInfo updateUserInfo(String token, UserUpdateRequest dto);

  void updateUserPassword(String token, PasswordUpdateRequest dto);

  void deleteUser(String token, String password);

  UserIntegrationInfo getUserInfo(String username);

  UserIntegrationInfo getUserInfoByToken(String token);

}
