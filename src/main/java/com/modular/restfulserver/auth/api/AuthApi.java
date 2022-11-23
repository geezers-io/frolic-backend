package com.modular.restfulserver.auth.api;

import com.modular.restfulserver.auth.application.AuthService;
import com.modular.restfulserver.auth.dto.TokenResponseDto;
import com.modular.restfulserver.auth.dto.UserLoginRequestDto;
import com.modular.restfulserver.auth.dto.UserSignupRequestDto;
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
  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<Map<String, TokenResponseDto>> signup(@RequestBody @Valid UserSignupRequestDto dto) {
    TokenResponseDto loginInfo = authService.saveUser(dto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseHelper.createDataMap(loginInfo));
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, TokenResponseDto>> login(@RequestBody @Valid UserLoginRequestDto dto) {
    var loginInfo = authService.loginUser(dto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseHelper.createDataMap(loginInfo));
  }

  @GetMapping("/reissue")
  public ResponseEntity<Map<String, Map<String, String>>> refresh(HttpServletRequest req) {
    String refreshToken = jwtProvider.getTokenByHttpRequestHeader(req);
    Map<String, String> tokens = authService.refresh(refreshToken);
    return ResponseEntity.ok(ResponseHelper.createDataMap(tokens));
  }

  @GetMapping("/logout")
  public ResponseEntity<Void> logoutApi(HttpServletRequest request) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    authService.logout(token);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
