package com.frolic.sns.util;

import com.frolic.sns.auth.dto.TokenInfo;

public interface TestAuthSupplier {

  TokenInfo getTokenInfo(TestUser user);
  void joinAllTestUser();

}
