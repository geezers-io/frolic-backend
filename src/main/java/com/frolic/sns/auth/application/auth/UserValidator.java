package com.frolic.sns.auth.application.auth;

import com.frolic.sns.auth.exception.AlreadyExistsUserException;
import com.frolic.sns.auth.exception.PasswordNotMatchException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserValidator {

  private final UserDslRepository userDslRepository;
  private final PasswordEncoder passwordEncoder;

  public void validateUserExists(String email, String username) {
    boolean isExistsEmail = userDslRepository.isExistsByEmail(email);
    boolean isExistsUsername = userDslRepository.isExistsUsername(username);
    if (isExistsEmail || isExistsUsername) throw new AlreadyExistsUserException();
  }

  public void validateUserPassword(User user, String targetPassword) {
    String userPassword = user.getPassword();
    boolean isMatchesPassword = passwordEncoder.matches(targetPassword, userPassword);
    if (!isMatchesPassword)
      throw new PasswordNotMatchException();
  }

}
