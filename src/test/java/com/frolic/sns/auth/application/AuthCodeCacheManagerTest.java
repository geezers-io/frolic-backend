package com.frolic.sns.auth.application;

import com.frolic.sns.auth.application.finder.AuthCode;
import com.frolic.sns.auth.application.finder.FinderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AuthCodeCacheManagerTest {

  protected final ValueOperations<String, AuthCode.MetaData> cacheKeyValueStore;

  protected final AuthCode authCode = AuthCode.builder()
    .addId(UUID.randomUUID())
    .addCode("55555")
    .addFinderType(FinderType.EMAIL)
    .addDestination("01026554276")
    .build();

  @Autowired
  AuthCodeCacheManagerTest(ValueOperations<String, AuthCode.MetaData> cacheKeyValueStore) {
    this.cacheKeyValueStore = cacheKeyValueStore;
  }

  @Test
  @DisplayName("키 밸류 스토어에 기존에 삽입했던 인증 코드 객체를 조회할 수 있다.")
  void getAuthCodeMetadata() {
    cacheKeyValueStore.set(authCode.getCode(), authCode.getAuthCodeMetaData());
    AuthCode.MetaData metaData = cacheKeyValueStore.get(authCode.getCode());
    assert metaData != null;
    assertThat(metaData.getCountOfAttempts()).isEqualTo(0);
    assertThat(metaData.getFinderType()).isEqualTo(FinderType.EMAIL);
  }

}