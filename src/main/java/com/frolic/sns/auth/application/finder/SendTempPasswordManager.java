package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.AuthCodeCacheManager;
import com.frolic.sns.auth.application.finder.common.UserInfoFindManager;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SendTempPasswordManager extends UserInfoFindManager implements UserInfoFinderSubManageable {

  public SendTempPasswordManager(AuthCodeCacheManager authCodeCacheManager) {
    super(authCodeCacheManager);
  }

  @Override
  public String authCodeVerify(UUID id, VerifyCodeRequest request) {
    return null;
  }

  @Override
  public void send(String receiver, String textContent) {
  }

}
