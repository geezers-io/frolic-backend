package com.frolic.sns.user.api;

import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.application.UserService;
import com.frolic.sns.user.dto.*;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.swagger.*;
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

  private final UserService userService;
  private final UserManager userManager;

  @UserInfoDocs
  @GetMapping("")
  public ResponseEntity<Map<String, UserUnitedInfo>> getUserInfoByTokenApi(HttpServletRequest request) {
    User user = userManager.getUserByHttpRequest(request);
    UserUnitedInfo userUnitedInfo = userService.getUserUnitedInfoByUser(user);
    return ResponseEntity.ok(ResponseHelper.createDataMap(userUnitedInfo));
  }

  @GetUserByUsernameDocs
  @GetMapping("/{username}")
  public ResponseEntity<Map<String, UserUnitedInfo>> getUserInfoByUsernameParamApi(@PathVariable String username) {
    UserUnitedInfo userUnitedInfo = userService.getUserUnitedDetail(username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(userUnitedInfo));
  }

  @UserUpdateDocs
  @PutMapping("")
  public ResponseEntity<Map<String, UserInfo>> updateUserInfoApi(
    HttpServletRequest request,
    @RequestBody @Valid UserUpdateRequest updateRequest
  ) {
    User user = userManager.getUserByHttpRequest(request);
    UserInfo userInfo = userService.updateUserDetail(user, updateRequest);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseHelper.createDataMap(userInfo));
  }

  @UserPasswordUpdateDocs
  @PatchMapping("/password")
  public ResponseEntity<Void> updateUserPasswordApi(
    HttpServletRequest request,
    @RequestBody @Valid PasswordUpdateRequest updateRequest
    ) {
    User user = userManager.getUserByHttpRequest(request);
    userService.updateUserPassword(user, updateRequest);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @UserDeleteDocs
  @DeleteMapping("")
  public ResponseEntity<Void> deleteUserApi(
    HttpServletRequest request,
    @RequestBody @Valid UserDeleteRequest userDeleteRequest
    ) {
    User user = userManager.getUserByHttpRequest(request);
    userService.deleteUser(user, userDeleteRequest.getPassword());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
