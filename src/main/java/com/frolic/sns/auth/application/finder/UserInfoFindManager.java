package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.exception.MisMatchFinderTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoFindManager implements UserInfoFindable {

  private final AuthCodeCacheManager authCodeCacheManager;
  private final int TTL_TIME = 10;

  @Override
  public AuthCode.MetaData getAuthCodeMedaData(String code, FinderType finderType) {
    AuthCode.MetaData metaData = authCodeCacheManager.getAuthenticationCode(code);
    if (!metaData.getFinderType().equals(finderType))
      throw new MisMatchFinderTypeException();
    return metaData;
  }

  @Override
  public void storeAuthCode(String code, FinderType finderType, String principalInfo) {
    AuthCode authCode = new AuthCode(code, finderType, principalInfo);
    authCodeCacheManager.storeAuthenticationCode(authCode, TTL_TIME);
  }

  public int getTimeToLive() {
    return TTL_TIME;
  }

}
