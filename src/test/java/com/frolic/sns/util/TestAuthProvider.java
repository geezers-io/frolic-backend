package com.frolic.sns.util;

import com.frolic.sns.auth.api.AuthApi;
import com.frolic.sns.auth.dto.TokenInfo;
import com.frolic.sns.auth.dto.UserLoginRequest;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TestAuthProvider implements TestAuthSupplier {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthApi authApi;

  @Autowired
  private EntityManager em;

  private final List<User> testUsers = Arrays.stream(TestUser.values())
    .collect(Collectors.toList())
    .stream().map(TestUser::getUser)
    .collect(Collectors.toList());


  public TokenInfo getTokenInfo(TestUser testUser) {
    UserLoginRequest userLoginRequest = MockProvider.createTestUserLoginRequest(testUser);
    return Objects.requireNonNull(
      authApi.login(userLoginRequest).getBody()
    ).get("data").getTokenInfo();
  }

  @Transactional
  public void joinAllTestUser() {
    testUsers
      .stream()
      .peek(user -> user.changePassword(passwordEncoder.encode(TestUser.testPassword)))
      .forEach(userRepository::save);
  }

  @Transactional
  public void clearAllTestUser() {
    List<String> emails = testUsers.stream().map(User::getEmail).collect(Collectors.toList());
    em.createQuery("DELETE FROM users u WHERE u.email in :emails")
      .setParameter("emails", emails)
      .executeUpdate();
  }

}
