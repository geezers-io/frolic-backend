package com.frolic.sns.auth.application.finder.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AuthCodeCacheManager {

  private final ValueOperations<String, AuthCode.MetaData> redisKeyValueStore;


  /**
   * @implNote cache 저장소에 인증코드 정보를 저장합니다. ( 제한 시간 10분 )
   * @param authCode 인증 코드 객체
   */
  public void storeAuthenticationCode(AuthCode authCode) {
    redisKeyValueStore.set(authCode.getId().toString(), authCode.getAuthCodeMetaData());
  }

  public void removeAuthenticationCode(UUID id) {
    redisKeyValueStore.getAndDelete(id.toString());
  }

  /**
   * @implNote 인증코드를 통해 해당 인증코드 객체의 부가 정보를 가져옵니다.
   * @param id 인증코드 정보를 가져올 인증코드 키 입니다.
   */
  public Optional<AuthCode.MetaData> getAuthenticationCode(UUID id) {
    return Optional.ofNullable(redisKeyValueStore.get(id.toString()));
  }

}
