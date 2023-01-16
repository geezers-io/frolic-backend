package com.frolic.sns.user.application;

import com.frolic.sns.user.dto.UserUnitedInfo;
import com.frolic.sns.user.dto.UserInfo;
import com.frolic.sns.user.dto.PasswordUpdateRequest;
import com.frolic.sns.user.dto.UserUpdateRequest;

public interface UserManageable {

  UserInfo updateUserDetail(String token, UserUpdateRequest dto);

  void updateUserPassword(String token, PasswordUpdateRequest dto);

  void deleteUser(String token, String password);

  UserUnitedInfo getUserUnitedDetail(String username);

  UserUnitedInfo getUserUnitedDetailByToken(String token);

}
