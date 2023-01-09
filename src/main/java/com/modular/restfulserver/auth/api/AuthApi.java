package com.modular.restfulserver.auth.api;

import com.modular.restfulserver.auth.application.AuthManager;
import com.modular.restfulserver.auth.dto.TokenInfo;
import com.modular.restfulserver.auth.dto.UserLoginRequest;
import com.modular.restfulserver.auth.dto.UserSignupRequest;
import com.modular.restfulserver.auth.swagger.*;
import com.modular.restfulserver.global.common.ResponseHelper;
import com.modular.restfulserver.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthApi {

  private final JwtProvider jwtProvider;
  private final AuthManager authManager;


  @SignupDocs
  @PostMapping("/signup")
  public ResponseEntity<Map<String, TokenInfo>> signup(@RequestBody @Valid UserSignupRequest dto) {
    TokenInfo loginInfo = authManager.saveUser(dto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseHelper.createDataMap(loginInfo));
  }

  @LoginDocs
  @PostMapping("/login")
  public ResponseEntity<Map<String, TokenInfo>> login(@RequestBody @Valid UserLoginRequest dto) {
    TokenInfo loginInfo = authManager.loginUser(dto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseHelper.createDataMap(loginInfo));
  }

  @ReissueTokenDocs
  @GetMapping("/reissue")
  public ResponseEntity<Map<String, Map<String, String>>> refresh(HttpServletRequest req) {
    String refreshToken = jwtProvider.getTokenByHttpRequestHeader(req);
    Map<String, String> tokens = authManager.refresh(refreshToken);
    return ResponseEntity.ok(ResponseHelper.createDataMap(tokens));
  }

  @LogoutDocs
  @GetMapping("/logout")
  public ResponseEntity<Void> logoutApi(HttpServletRequest request) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    authManager.logout(token);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
