package com.frolic.sns.user.swagger;

import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "회원 수정 API",
  description = "회원의 모든 정보를 전달받아 정보를 수정합니다.",
  tags = SwaggerMessageUtils.UserManagementApi,
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "OK",
      content = @Content(schema = @Schema(implementation = UserManagementSchemas.UserInfoForClientSchema.class))
    )
  },
  security = { @SecurityRequirement(name = "bearer-key") }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserUpdateDocs {}
