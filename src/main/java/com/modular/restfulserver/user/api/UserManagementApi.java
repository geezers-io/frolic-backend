package com.modular.restfulserver.user.api;

import com.modular.restfulserver.global.common.ResponseHelper;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.application.UserManager;
import com.modular.restfulserver.user.dto.*;
import com.modular.restfulserver.user.swagger.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserManagementApi {

  private final UserManager userManager;
  private final JwtProvider jwtProvider;


  @UserInfoDocs
  @GetMapping("")
  public ResponseEntity<Map<String, UserUnitedDetails>> getUserInfoByTokenApi(HttpServletRequest request) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    UserUnitedDetails userInfo = userManager.getUserInfoByToken(token);
    return ResponseEntity.ok(ResponseHelper.createDataMap(userInfo));
  }

  @GetUserByUsernameDocs
  @GetMapping("/{username}")
  public ResponseEntity<Map<String, UserUnitedDetails>> getUserInfoByUsernameParamApi(@PathVariable String username) {
    UserUnitedDetails userInfo = userManager.getUserInfo(username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(userInfo));
  }

  @UserUpdateDocs
  @PutMapping("")
  public ResponseEntity<Map<String, UserDetails>> updateUserInfoApi(
    HttpServletRequest request,
    @RequestBody @Valid UserUpdateRequest dto
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    UserDetails userDetails = userManager.updateUserInfo(token, dto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseHelper.createDataMap(userDetails));
  }

  @UserPasswordUpdateDocs
  @PatchMapping("/password")
  public ResponseEntity<Void> updateUserPasswordApi(
    HttpServletRequest request,
    @RequestBody @Valid PasswordUpdateRequest dto
    ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    userManager.updateUserPassword(token, dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @UserDeleteDocs
  @DeleteMapping("")
  public ResponseEntity<Void> deleteUserApi(
    HttpServletRequest request,
    @RequestBody @Valid UserDeleteRequest dto
    ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    userManager.deleteUser(token, dto.getPassword());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
