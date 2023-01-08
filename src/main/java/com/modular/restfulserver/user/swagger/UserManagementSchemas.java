package com.modular.restfulserver.user.swagger;

import com.modular.restfulserver.user.dto.UserIntegrationInfo;
import com.modular.restfulserver.user.dto.UserInfo;
import lombok.Getter;

public class UserManagementSchemas {

  @Getter
  static class UserInfoSchema {
    UserIntegrationInfo data;
  }

  @Getter
  static class UserInfoForClientSchema {
    UserInfo data;
  }

}
