package com.modular.restfulserver.user.swagger;

import com.modular.restfulserver.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Operation(summary = "이름으로 회원 정보 조회 API", description = "회원 이름을 이용해 회원 정보를 조회합니다.", tags = SwaggerMessageUtils.UserManagementApi)
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation =
    UserManagementSchemas.UserInfoSchema.class
  ))),
})
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface GetUserByUsernameDocs { }
