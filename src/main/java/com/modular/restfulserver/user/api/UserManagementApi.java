package com.modular.restfulserver.user.api;

import com.modular.restfulserver.global.common.ResponseHelper;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.application.UserManager;
import com.modular.restfulserver.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @Operation(summary = "회원 정보 요청", description = "회원 자신의 정보가 제공됩니다.", tags = { "사용자 관리 API" })
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation =
      UserInfoDto.class
    ))),
  })
  @GetMapping("")
  public ResponseEntity<Map<String, UserInfoDto>> getUserInfoByTokenApi(HttpServletRequest request) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    UserInfoDto userInfo = userManager.getUserInfoByToken(token);
    return ResponseEntity.ok(ResponseHelper.createDataMap(userInfo));
  }

  @GetMapping("/{username}")
  public ResponseEntity<Map<String, UserInfoDto>> getUserInfoByUsernameParamApi(@PathVariable String username) {
    UserInfoDto userInfo = userManager.getUserInfo(username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(userInfo));
  }

  @PutMapping("")
  public ResponseEntity<Map<String, UserInfoForClientDto>> updateUserInfoApi(
    HttpServletRequest request,
    @RequestBody @Valid UserUpdateRequestDto dto
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    UserInfoForClientDto userInfo = userManager.updateUserInfo(token, dto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseHelper.createDataMap(userInfo));
  }

  @PatchMapping("/password")
  public ResponseEntity<Void> updateUserPasswordApi(
    HttpServletRequest request,
    @RequestBody @Valid UserUpdatePasswordDto dto
    ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    userManager.updateUserPassword(token, dto);
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
