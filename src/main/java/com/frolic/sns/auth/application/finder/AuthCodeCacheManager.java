package com.frolic.sns.auth.application.finder;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthCodeCacheManager {

  private final ValueOperations<String, AuthCode.MetaData> redisKeyValueStore;

  /**
   * @implNote cache 저장소에 인증코드 정보를 저장합니다. ( 제한 시간 10분 )
   * @param authCode 인증 코드 객체
   */
  public void storeAuthenticationCode(AuthCode authCode, int minutes) {
    String stringId = getStringId(authCode.getId());
    redisKeyValueStore.set(stringId, authCode.getAuthCodeMetaData(), minutes, TimeUnit.MINUTES);
  }

  public void removeAuthenticationCode(UUID id) {
    String stringId = getStringId(id);
    redisKeyValueStore.getAndDelete(stringId);
  }

  /**
   * @implNote 인증코드를 통해 해당 인증코드 객체의 부가 정보를 가져옵니다.
   * @param id 인증코드 정보를 가져올 인증코드 키 입니다.
   */
  public AuthCode.MetaData getAuthenticationCode(UUID id) {
    return redisKeyValueStore.get(getStringId(id));
  }

  private String getStringId(UUID id) {
    return id.toString();
  }

}
