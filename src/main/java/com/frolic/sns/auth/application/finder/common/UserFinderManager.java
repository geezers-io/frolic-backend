package com.frolic.sns.auth.application.finder.common;

import com.frolic.sns.auth.exception.MisMatchFinderTypeException;
import com.frolic.sns.global.exception.NotFoundResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserFinderManager {

  private final AuthCodeCacheManager authCodeCacheManager;

  public AuthCode.MetaData getAuthCode(UUID id, FinderType finderType) {
    AuthCode.MetaData metaData = authCodeCacheManager.getAuthenticationCode(id)
      .orElseThrow(NotFoundResourceException::new);
    if (!metaData.getFinderType().equals(finderType))
      throw new MisMatchFinderTypeException();
    return metaData;
  }

  public void storeAuthCode(AuthCode authCode) {
    authCodeCacheManager.storeAuthenticationCode(authCode);
  }

  public UUID createId() {
    return UUID.randomUUID();
  }

  public void removeAuthCode(UUID id) {
    authCodeCacheManager.removeAuthenticationCode(id);
  }

  public String createCode() {
    return String.valueOf(
      (int)(Math.random() * (999999 - 100000 + 1)) + 100000
    );
  }

}
