package com.modular.restfulserver.user.swagger;

import com.modular.restfulserver.user.dto.UserUnitedInfo;
import com.modular.restfulserver.user.dto.UserInfo;
import lombok.Getter;

public class UserManagementSchemas {

  @Getter
  static class UserInfoSchema {
    UserUnitedInfo data;
  }

  @Getter
  static class UserInfoForClientSchema {
    UserInfo data;
  }

}
