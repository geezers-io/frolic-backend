package com.frolic.sns.auth.application.finder.common;

import com.frolic.sns.auth.application.finder.common.AuthCode;
import com.frolic.sns.auth.application.finder.common.FinderType;

import java.util.UUID;

public interface UserInfoFindable {
  AuthCode.MetaData getAuthCode(UUID id, FinderType finderType);

  void storeAuthCode(AuthCode authCode);

  UUID createId();

  void removeAuthCode(UUID id);

  String createCode();

}
