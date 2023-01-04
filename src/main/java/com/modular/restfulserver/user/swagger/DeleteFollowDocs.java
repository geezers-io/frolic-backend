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
  summary = "팔로우 취소 API",
  description = "요청 대상과 팔로우 관계를 제거합니다.",
  tags = SwaggerMessageUtils.FollowApi,
  security = { @SecurityRequirement(name = "bearer-key") }
)
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "OK"),
})
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteFollowDocs {}
