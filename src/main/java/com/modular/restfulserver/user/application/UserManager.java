package com.modular.restfulserver.user.application;

import com.modular.restfulserver.user.dto.UserInfoDto;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import com.modular.restfulserver.user.dto.UserUpdatePasswordDto;
import com.modular.restfulserver.user.dto.UserUpdateRequestDto;

public interface UserManager {

  UserInfoForClientDto updateUserInfo(String token, UserUpdateRequestDto dto);

  void updateUserPassword(String token, UserUpdatePasswordDto dto);

  void deleteUser(String token, String password);

  UserInfoDto getUserInfo(String username);

  UserInfoDto getUserInfoByToken(String token);

}
