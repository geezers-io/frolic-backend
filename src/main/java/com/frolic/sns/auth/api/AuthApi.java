package com.frolic.sns.auth.api;

import com.frolic.sns.auth.application.auth.AuthService;
import com.frolic.sns.auth.dto.AccessTokenInfo;
import com.frolic.sns.auth.dto.UserLoginRequest;
import com.frolic.sns.auth.dto.UserLoginResponse;
import com.frolic.sns.auth.dto.UserSignupRequest;
import com.frolic.sns.auth.swagger.*;
import com.frolic.sns.auth.application.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static com.frolic.sns.global.common.ResponseHelper.createDataMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthApi {

  private final JwtProvider jwtProvider;
  private final AuthService authService;

  @SignupDocs
  @PostMapping("/signup")
  public ResponseEntity<Map<String, UserLoginResponse>> signup(@RequestBody @Valid UserSignupRequest dto) {
    UserLoginResponse loginInfo = authService.signup(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createDataMap(loginInfo));
  }

  @LoginDocs
  @PostMapping("/login")
  public ResponseEntity<Map<String, UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest dto) {
    UserLoginResponse loginInfo = authService.login(dto);
    return ResponseEntity.status(HttpStatus.OK).body(createDataMap(loginInfo));
  }

  @ReissueTokenDocs
  @GetMapping("/reissue")
  public ResponseEntity<Map<String, AccessTokenInfo>> refresh(HttpServletRequest req) {
    String refreshToken = jwtProvider.getTokenByHttpRequestHeader(req);
    AccessTokenInfo newAccessToken = authService.issueNewAccessToken(refreshToken);
    return ResponseEntity.ok(createDataMap(newAccessToken));
  }

  @LogoutDocs
  @GetMapping("/logout")
  public ResponseEntity<Void> logoutApi(HttpServletRequest request) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    authService.logout(token);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
