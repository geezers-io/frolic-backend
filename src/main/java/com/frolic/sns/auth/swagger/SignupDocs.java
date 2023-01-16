package com.frolic.sns.auth.swagger;

import com.frolic.sns.global.swagger.SwaggerCommonErrorSchema;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Operation(summary = "회원가입 요청", description = "사용자 요청에 의해 회원가입을 처리합니다.", tags = SwaggerMessageUtils.AuthApi)
@ApiResponses(value = {
  @ApiResponse(
    responseCode = "201",
    description = "Created",
    content = @Content(schema = @Schema(implementation =
      AuthSchemas.Signup.class
    ))
  ),
  @ApiResponse(responseCode = "400", description = "Bad Request (폼 검증 실패)", content = @Content(schema = @Schema(
    implementation = SwaggerCommonErrorSchema.class
  ))),
  @ApiResponse(responseCode = "400", description = "Bad Request (이미 존재하는 회원)", content = @Content(schema = @Schema(
    implementation = SwaggerCommonErrorSchema.class
  )))
})
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SignupDocs {}
