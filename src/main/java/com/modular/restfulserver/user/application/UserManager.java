package com.modular.restfulserver.user.application;

import com.modular.restfulserver.user.dto.UserUnitedDetails;
import com.modular.restfulserver.user.dto.UserDetails;
import com.modular.restfulserver.user.dto.PasswordUpdateRequest;
import com.modular.restfulserver.user.dto.UserUpdateRequest;

public interface UserManager {

  UserDetails updateUserInfo(String token, UserUpdateRequest dto);

  void updateUserPassword(String token, PasswordUpdateRequest dto);

  void deleteUser(String token, String password);

  UserUnitedDetails getUserInfo(String username);

  UserUnitedDetails getUserInfoByToken(String token);

}
