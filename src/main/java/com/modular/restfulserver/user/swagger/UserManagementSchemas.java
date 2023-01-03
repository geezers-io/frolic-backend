package com.modular.restfulserver.user.swagger;

import com.modular.restfulserver.user.dto.UserInfoDto;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import lombok.Getter;

public class UserManagementSchemas {

  @Getter
  static class UserInfoSchema {
    UserInfoDto data;
  }

  @Getter
  static class UserInfoForClientSchema {
    UserInfoForClientDto data;
  }

}
