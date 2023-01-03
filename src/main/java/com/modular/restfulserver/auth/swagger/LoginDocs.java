package com.modular.restfulserver.auth.swagger;

import com.modular.restfulserver.global.swagger.SwaggerCommonErrorSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Operation(
  summary = "로그인 요청",
  description = "로그인 요청에 의해 액세스 토큰과 검증 토큰을 발행합니다.",
  tags = "인증 및 인가 API"
)
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation =
    AuthSchemas.Signup.class
  ))),
  @ApiResponse(responseCode = "400", description = "Bad Request (폼 검증 실패)", content = @Content(schema = @Schema(
    implementation = SwaggerCommonErrorSchema.class
  )))
})
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginDocs {}
