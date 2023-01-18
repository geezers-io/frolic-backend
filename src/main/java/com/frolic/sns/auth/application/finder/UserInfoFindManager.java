package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.exception.MisMatchFinderTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserInfoFindManager implements UserInfoFindable {

  private final AuthCodeCacheManager authCodeCacheManager;

  @Override
  public AuthCode.MetaData getAuthCode(UUID id, FinderType finderType) {
    AuthCode.MetaData metaData = authCodeCacheManager.getAuthenticationCode(id);
    if (!metaData.getFinderType().equals(finderType))
      throw new MisMatchFinderTypeException();
    return metaData;
  }

  @Override
  public void storeAuthCode(AuthCode authCode) {
    authCodeCacheManager.storeAuthenticationCode(authCode);
  }

  @Override
  public UUID createId() {
    return UUID.randomUUID();
  }

  @Override
  public void removeAuthCode(UUID id) {
    authCodeCacheManager.removeAuthenticationCode(id);
  }

  @Deprecated
  @Override
  public String createCode() {
    return String.valueOf( Math.floor((Math.random() * 100000) + 999999));
  }

}
