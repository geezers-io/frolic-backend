package com.modular.restfulserver.user.application;

import com.modular.restfulserver.user.dto.UserUnitedInfo;
import com.modular.restfulserver.user.dto.UserInfo;
import com.modular.restfulserver.user.dto.PasswordUpdateRequest;
import com.modular.restfulserver.user.dto.UserUpdateRequest;

public interface UserManageable {

  UserInfo updateUserDetail(String token, UserUpdateRequest dto);

  void updateUserPassword(String token, PasswordUpdateRequest dto);

  void deleteUser(String token, String password);

  UserUnitedInfo getUserUnitedDetail(String username);

  UserUnitedInfo getUserUnitedDetailByToken(String token);

}
