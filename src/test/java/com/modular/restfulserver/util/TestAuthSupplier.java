package com.modular.restfulserver.util;

import com.modular.restfulserver.auth.dto.TokenInfo;

public interface TestAuthSupplier {

  TokenInfo getTokenInfo(TestUser user);
  void joinAllTestUser();

}
