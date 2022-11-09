package com.modular.restfulserver.user.api;

import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.application.UserManager;
import com.modular.restfulserver.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserManagementApi {

  private final UserManager userManager;
  private final JwtProvider jwtProvider;

  @GetMapping("/")
  public ResponseEntity<Map<String, UserInfoDto>> getUserInfoByTokenApi(
    HttpServletRequest request
  ) {
    Map<String, UserInfoDto> responseData = new HashMap<>();
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    UserInfoDto userInfo = userManager.getUserInfoByToken(token);
    responseData.put("data", userInfo);
    return ResponseEntity.ok(responseData);
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/{username}")
  public ResponseEntity<Map<String, UserInfoDto>> getUserInfoByUsernameParamApi(
    @PathVariable String username
  ) {
    Map<String, UserInfoDto> responseData = new HashMap<>();
    UserInfoDto userInfo = userManager.getUserInfo(username);
    responseData.put("data", userInfo);
    return ResponseEntity.ok(responseData);
  }
}
