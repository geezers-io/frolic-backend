package com.frolic.sns.auth.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RefreshTokenCacheManager {

  private final ValueOperations<String, String> store;

  public void store(String userEmail, String token) {
    store.set(userEmail, token);
  }

  public Optional<String> getToken(String userEmail) {
    return Optional.ofNullable(store.get(userEmail));
  }

}
