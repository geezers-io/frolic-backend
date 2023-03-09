package com.frolic.sns.auth.application.auth;

import com.frolic.sns.auth.dto.TokenInfo;
import com.frolic.sns.auth.dto.UserLoginRequest;
import com.frolic.sns.auth.dto.UserLoginResponse;
import com.frolic.sns.auth.dto.UserSignupRequest;
import com.frolic.sns.auth.exception.AlreadyExistsUserException;
import com.frolic.sns.auth.exception.InvalidTokenException;
import com.frolic.sns.auth.exception.PasswordNotMatchException;
import com.frolic.sns.global.config.security.CustomEmailPasswordAuthToken;
import com.frolic.sns.global.config.security.JwtConstants;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.user.application.UserService;
import com.frolic.sns.user.dto.UserUnitedInfo;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthManager implements AuthManageable {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  public UserLoginResponse saveUser(UserSignupRequest userSignupRequest) {
    boolean isExistsEmail = userRepository.existsByEmail(userSignupRequest.getEmail());
    boolean isExistsUsername = userRepository.existsByUsername(userSignupRequest.getUsername());
    if (isExistsEmail || isExistsUsername)
      throw new AlreadyExistsUserException();

    String rawPassword = userSignupRequest.getPassword();
    String encodedPassword = passwordEncoder.encode(rawPassword);
    userSignupRequest.setEncodePassword(encodedPassword);
    userRepository.save(userSignupRequest.toEntity());

    UserLoginRequest userLoginRequest = UserLoginRequest.builder()
      .addEmail(userSignupRequest.getEmail())
      .addPassword(rawPassword)
      .build();

    return this.loginUser(userLoginRequest);
  }

  public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
    User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(UserNotFoundException::new);

    if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
      throw new PasswordNotMatchException();

    CustomEmailPasswordAuthToken emailPasswordAuthToken = new CustomEmailPasswordAuthToken(userLoginRequest.getEmail());
    Authentication authentication = authenticationManager.authenticate(emailPasswordAuthToken);

    String email = authentication.getName();

    String accessToken = jwtProvider.createAccessToken(email);
    String refreshToken = jwtProvider.createRefreshToken(email);
    user.updateRefreshToken(refreshToken);

    TokenInfo tokenInfo = TokenInfo.builder()
      .addAccessToken(accessToken)
      .addRefreshToken(refreshToken)
      .build();
    UserUnitedInfo userUnitedInfo = userService.getUserUnitedDetail(user.getUsername());

    return UserLoginResponse.create(tokenInfo, userUnitedInfo);
  }

  public Map<String, String> refresh(String refreshToken) {
    String email = jwtProvider.getUserEmailByToken(refreshToken);
    Map<String, String> response = new HashMap<>();
    String newAccessToken = jwtProvider.createAccessToken(email);
    String userRegisteredToken = userRepository.getUserRefreshToken(email);

    if (!refreshToken.equals(userRegisteredToken))
      throw new InvalidTokenException();
    response.put(JwtConstants.ACCESS_TOKEN, newAccessToken);

    return response;
  }

  public void logout(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    user.updateRefreshToken(null);
  }

}
