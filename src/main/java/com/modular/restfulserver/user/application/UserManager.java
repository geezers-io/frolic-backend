package com.modular.restfulserver.user.application;

import com.modular.restfulserver.user.dto.UserInfoDto;
import com.modular.restfulserver.user.dto.UserUpdateRequestDto;

public interface UserManager {

  void updateUserInfo(String token, UserUpdateRequestDto dto);

  void deleteUser(String token);

  UserInfoDto getUserInfo(String username);

  UserInfoDto getUserInfoByToken(String token);

}
