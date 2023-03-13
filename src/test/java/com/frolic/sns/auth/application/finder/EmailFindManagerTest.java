package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.AuthCode;
import com.frolic.sns.auth.application.finder.common.FinderType;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import com.frolic.sns.auth.exception.OverTriedAuthCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailFindManagerTest {

  @Autowired
  protected EmailFindManager emailFindManager;

  UUID id;

  @BeforeEach
  void beforeEach() {
    id = emailFindManager.createId();
    String code = emailFindManager.createCode();
    emailFindManager.storeAuthCode(
      AuthCode.builder()
        .addId(id)
        .addCode(code)
        .addDestination("01026554276")
        .addCountOfAttempts(0)
        .addFinderType(FinderType.EMAIL)
        .addLocalTime(LocalTime.now())
        .build()
    );
  }

  @Test
  @DisplayName("사용자가 저장한 인증 코드 값을 정상적으로 불러온다.")
  void createAndLoadAuthCode() {
    // given

    // when
    AuthCode.MetaData metaData = emailFindManager.getAuthCode(id, FinderType.EMAIL);

    // then
    Assertions.assertNotNull(metaData);
    System.out.println("metaData.destination: " + metaData.getDestination());
  }

  @Test
  @DisplayName("인증 코드 검증을 실패한다.")
  void verifyOnceFailed() {
    // given
    VerifyCodeRequest request = new VerifyCodeRequest("wrongCode");

    // then
    assertThrows(MisMatchAuthCodeException.class, () -> emailFindManager.verifyAuthCode(id, request));
  }

  @Test
  @DisplayName("인증 코드 시도가 5회 초과 시 에러가 발생한다.")
  void verifyOver() {
    // given
    VerifyCodeRequest request = new VerifyCodeRequest("wrongCode");

    // when
    for (int i = 0; i < 5; i++) {
      assertThrows(MisMatchAuthCodeException.class, () -> {
        emailFindManager.verifyAuthCode(id, request);
      });
    }

    // then
    assertThrows(OverTriedAuthCodeException.class, () -> emailFindManager.verifyAuthCode(id, request));
  }

}