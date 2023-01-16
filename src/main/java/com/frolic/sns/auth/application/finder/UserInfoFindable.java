package com.frolic.sns.auth.application.finder;


public interface UserInfoFindable {
  AuthCode.MetaData getAuthCodeMedaData(String code, FinderType finderType);

  void storeAuthCode(String code, FinderType finderType, String principalInfo);

}
