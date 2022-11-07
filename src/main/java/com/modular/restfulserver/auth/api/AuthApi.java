package com.modular.restfulserver.auth.api;

import com.modular.restfulserver.auth.application.AuthService;
import com.modular.restfulserver.auth.dto.UserLoginRequestDto;
import com.modular.restfulserver.auth.dto.UserSignupRequestDto;
import com.modular.restfulserver.auth.exception.NoneTokenOnHeaderException;
import com.modular.restfulserver.global.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.modular.restfulserver.global.config.security.JwtConstants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthApi {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<Void> signup(
    @RequestBody @Valid UserSignupRequestDto dto
    ) {
    authService.saveUser(dto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleDtoMethodArgumentNotValidExceptionHandler(
    MethodArgumentNotValidException ex
  ) {
    return ErrorResponse.toResponseEntityByArgumentNotValidException(ex);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(
    @RequestBody @Valid UserLoginRequestDto dto
    ) {
    var data = authService.loginUser(dto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(data);
  }

  @GetMapping("/reissue")
  public ResponseEntity<Map<String, Map<String, String>>> refresh(
    HttpServletRequest req
  ) {
    String authorizationHeader = req.getHeader("AUTHORIZATION");
    String refreshToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
    Map<String, String> tokens = authService.refresh(refreshToken);
    Map<String, Map<String, String>> response = new HashMap<>();
    response.put("data", tokens);

    return ResponseEntity.ok(response);
  }

}
