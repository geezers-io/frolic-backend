package com.modular.restfulserver.auth.application;

import com.modular.restfulserver.auth.dto.TokenResponseDto;
import com.modular.restfulserver.auth.dto.UserLoginRequestDto;
import com.modular.restfulserver.auth.dto.UserSignupRequestDto;
import com.modular.restfulserver.auth.exception.AlreadyExistsUserException;
import com.modular.restfulserver.auth.exception.InvalidTokenException;
import com.modular.restfulserver.auth.exception.PasswordNotMatchException;
import com.modular.restfulserver.global.config.security.CustomEmailPasswordAuthToken;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
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
public class AuthService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public TokenResponseDto saveUser(UserSignupRequestDto dto) {
    boolean isExistsEmail = userRepository.existsByEmail(dto.getEmail());
    boolean isExistsUsername = userRepository.existsByUsername(dto.getUsername());
    if (isExistsEmail || isExistsUsername)
      throw new AlreadyExistsUserException();

    String rawPassword = dto.getPassword();
    String encodedPassword = passwordEncoder.encode(rawPassword);
    dto.setEncodePassword(encodedPassword);
    userRepository.save(dto.toEntity());

    UserLoginRequestDto userLoginRequestDto = UserLoginRequestDto.builder()
      .addUsername(dto.getUsername())
      .addEmail(dto.getEmail())
      .addPassword(rawPassword)
      .build();
    return this.loginUser(userLoginRequestDto);
  }

  public TokenResponseDto loginUser(UserLoginRequestDto dto) {
    User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(UserNotFoundException::new);

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
      throw new PasswordNotMatchException();

    CustomEmailPasswordAuthToken emailPasswordAuthToken = new CustomEmailPasswordAuthToken(dto.getEmail());
    Authentication authentication = authenticationManager.authenticate(emailPasswordAuthToken);

    String email = authentication.getName();

    String accessToken = jwtProvider.createAccessToken(email);
    String refreshToken = jwtProvider.createRefreshToken(email);
    user.updateRefreshToken(refreshToken);

    return TokenResponseDto.builder()
      .addAccessToken(accessToken)
      .addRefreshToken(refreshToken)
      .addUserInfo(UserInfoForClientDto.from(user))
      .build();
  }

  public Map<String, String> refresh(String refreshToken) {
    String email = jwtProvider.getUserEmailByToken(refreshToken);
    Map<String, String> response = new HashMap<>();
    String newAccessToken = jwtProvider.createAccessToken(email);
    String userRegisteredToken = userRepository.getUserRefreshToken(email);
    if (!refreshToken.equals(userRegisteredToken))
      throw new InvalidTokenException();
    response.put("accessToken", newAccessToken);
    return response;
  }

  public void logout(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userRepository.findByEmail(email)
      .orElseThrow(UserNotFoundException::new);
    user.updateRefreshToken(null);
  }

}
