package com.modular.restfulserver.user.api;

import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.application.UserManager;
import com.modular.restfulserver.user.dto.UserDeletePasswordDto;
import com.modular.restfulserver.user.dto.UserInfoDto;
import com.modular.restfulserver.user.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserManagementApi {

  private final UserManager userManager;
  private final JwtProvider jwtProvider;

  @GetMapping("")
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

  @PutMapping("")
  public ResponseEntity<Void> updateUserInfoApi(
    HttpServletRequest request,
    @RequestBody @Valid UserUpdateRequestDto dto
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    userManager.updateUserInfo(token, dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("")
  public ResponseEntity<Void> deleteUserApi(
    HttpServletRequest request,
    @RequestBody @Valid UserDeletePasswordDto dto
    ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    userManager.deleteUser(token, dto.getPassword());
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
