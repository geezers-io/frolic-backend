package com.modular.restfulserver.user.swagger;

import com.modular.restfulserver.global.swagger.CommonAuthError;
import com.modular.restfulserver.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "회원 삭제 API",
  description = "회원의 토큰 정보를 검증 후 회원을 비활성화 합니다.",
  tags = SwaggerMessageUtils.UserManagementApi,
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "OK"
    )
  },
  security = { @SecurityRequirement(name = "bearer-key") }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserDeleteDocs {}
