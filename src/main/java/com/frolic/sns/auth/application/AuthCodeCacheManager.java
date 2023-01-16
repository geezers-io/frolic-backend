package com.frolic.sns.auth.application;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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
  public void storeAuthenticationCode(AuthCode authCode) {
    redisKeyValueStore.set(authCode.getCode(), authCode.getAuthCodeMetaData(), 10, TimeUnit.MINUTES);
  }

  /**
   * @implNote 인증코드를 통해 해당 인증코드 객체의 부가 정보를 가져옵니다.
   * @param code 인증코드 정보를 가져올 인증코드 키 입니다.
   */
  public AuthCode.MetaData getAuthenticationCode(String code) {
    return redisKeyValueStore.get(code);
  }


}
