package com.frolic.sns.auth.application.finder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailFindManagerTest {

  @Autowired
  protected EmailFindManager emailFindManager;

  @Test
  @DisplayName("사용자가 저장한 인증 코드 값을 정상적으로 불러온다.")
  void createAuthCode() {
    UUID id = emailFindManager.createId();
    String code = emailFindManager.createCode();
    emailFindManager.storeAuthCode(id, code, FinderType.EMAIL, "01026554276");
    AuthCode.MetaData metaData = emailFindManager.getAuthCode(id, FinderType.EMAIL);
    Assertions.assertNotNull(metaData);
  }

}