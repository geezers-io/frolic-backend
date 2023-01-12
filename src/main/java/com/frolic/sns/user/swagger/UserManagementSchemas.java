package com.frolic.sns.user.swagger;

import com.frolic.sns.user.dto.UserUnitedInfo;
import com.frolic.sns.user.dto.UserInfo;
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
