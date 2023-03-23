package com.frolic.sns.auth.application.auth;

import com.frolic.sns.auth.application.security.*;
import com.frolic.sns.auth.dto.*;
import com.frolic.sns.auth.exception.InvalidTokenException;
import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.application.info.UserEmail;
import com.frolic.sns.user.dto.UserUnitedInfo;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final TokenCreator tokenCreator;
  private final UserManager userManager;
  private final UserValidator userValidator;
  private final RefreshTokenCacheManager tokenCacheManager;

  public UserLoginResponse signup(UserSignupRequest userSignupRequest) {
    userValidator.validateUserExists(userSignupRequest.getEmail(), userSignupRequest.getUsername());
    String rawPassword = userSignupRequest.getPassword();
    String encodedPassword = passwordEncoder.encode(rawPassword);
    userSignupRequest.setEncodePassword(encodedPassword);
    userRepository.save(userSignupRequest.toEntity());

    UserLoginRequest userLoginRequest = UserLoginRequest.builder()
      .addEmail(userSignupRequest.getEmail())
      .addPassword(rawPassword)
      .build();

    return this.login(userLoginRequest);
  }

  public UserLoginResponse login(UserLoginRequest userLoginRequest) {
    String userEmail = userLoginRequest.getEmail();
    User user = userManager.getUser(new UserEmail(userEmail));
    userValidator.validateUserPassword(user, userLoginRequest.getPassword());

    String accessToken = tokenCreator.createToken(userEmail, TokenType.ACCESS_TOKEN);
    String refreshToken = tokenCreator.createToken(userEmail, TokenType.REFRESH_TOKEN);

    FrolicAuthenticationToken emailPasswordAuthToken = new FrolicAuthenticationToken(userEmail, accessToken);
    authenticationManager.authenticate(emailPasswordAuthToken);
    tokenCacheManager.store(userEmail, refreshToken);

    TokenInfo tokenInfo = TokenInfo.builder()
      .addAccessToken(accessToken)
      .addRefreshToken(refreshToken)
      .build();
    UserUnitedInfo userUnitedInfo = userManager.getUserUnitedInfo(user);

    return UserLoginResponse.create(tokenInfo, userUnitedInfo);
  }

  public AccessTokenInfo issueNewAccessToken(String refreshToken) {
    Claims claims = jwtProvider.parseClaims(refreshToken);
    String userEmail = (String) claims.get(TokenKey.USER_EMAIL.name());
    String tokenType = (String) claims.get(TokenKey.TOKEN_TYPE.name());

    if (!tokenType.equals(TokenType.REFRESH_TOKEN.name()))
      throw new InvalidTokenException();

    String newAccessToken = tokenCreator.createToken(userEmail, TokenType.ACCESS_TOKEN);
    String userRegisteredToken = tokenCacheManager.getToken(userEmail).orElseThrow(InvalidTokenException::new);

    if (!refreshToken.equals(userRegisteredToken))
      throw new InvalidTokenException();

    return new AccessTokenInfo(newAccessToken);
  }

  public void logout(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userManager.getUser(new UserEmail(email));
    user.updateRefreshToken(null);
  }

}
