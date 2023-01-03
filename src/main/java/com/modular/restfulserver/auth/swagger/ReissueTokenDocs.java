package com.modular.restfulserver.auth.swagger;

import com.modular.restfulserver.global.swagger.CommonAuthError;
import com.modular.restfulserver.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "액세스 토큰 재발행",
  description = "검증 토큰을 확인하고 유효하다면 액세스 토큰을 재발행합니다.",
  tags = SwaggerMessageUtils.AuthApi,
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "OK",
      content = @Content(schema = @Schema(implementation = AuthSchemas.ReissueToken.class))
    )
  },
  security = { @SecurityRequirement(name = "bearer-key") }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ReissueTokenDocs {}
