package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.AuthCode;
import com.frolic.sns.auth.dto.VerifyCodeRequest;

import java.util.UUID;

/**
 * @implNote UserInfoFinderManager 를 상속받은 하위 관리자 객체에 대한 명세입니다.
 */
public interface UserFinderManageable {
  AuthCode.MetaData verifyAuthCode(UUID id, VerifyCodeRequest request);

  void send(String receiver, String textContent);

}
