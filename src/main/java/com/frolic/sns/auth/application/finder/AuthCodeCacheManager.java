package com.frolic.sns.auth.application.finder;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    redisKeyValueStore.set(authCode.getCode(), authCode.getAuthCodeMetaData(), minutes, TimeUnit.MINUTES);
  }

  /**
   * @implNote 인증코드를 통해 해당 인증코드 객체의 부가 정보를 가져옵니다.
   * @param code 인증코드 정보를 가져올 인증코드 키 입니다.
   */
  public AuthCode.MetaData getAuthenticationCode(String code) {
    return redisKeyValueStore.get(code);
  }


}
