package com.modular.restfulserver.user.swagger;

import com.modular.restfulserver.user.dto.UserUnitedDetails;
import com.modular.restfulserver.user.dto.UserDetails;
import lombok.Getter;

public class UserManagementSchemas {

  @Getter
  static class UserInfoSchema {
    UserUnitedDetails data;
  }

  @Getter
  static class UserInfoForClientSchema {
    UserDetails data;
  }

}
