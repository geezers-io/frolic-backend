package com.modular.restfulserver.user.swagger;

import com.modular.restfulserver.global.swagger.CommonAuthError;
import com.modular.restfulserver.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "회원 정보 요청 API",
  description = "회원 자신의 정보가 제공됩니다.",
  tags = SwaggerMessageUtils.UserManagementApi,
  security = { @SecurityRequirement(name = "bearer-key") }
)
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation =
    UserManagementSchemas.UserInfoSchema.class
  ))),
})
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfoDocs {}
