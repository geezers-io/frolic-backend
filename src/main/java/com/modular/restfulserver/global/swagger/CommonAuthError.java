package com.modular.restfulserver.global.swagger;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@ApiResponses({
  @ApiResponse(responseCode = "401", description = "토큰 유효성 검증 실패", content = @Content(schema = @Schema(
    implementation = SwaggerCommonErrorSchema.class
  ))),
  @ApiResponse(responseCode = "403", description = "헤더에서 토큰 값을 찾을 수 없음", content = @Content(schema = @Schema(
    implementation = SwaggerCommonErrorSchema.class
  )))
})
@Target(ElementType.ANNOTATION_TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonAuthError {}
