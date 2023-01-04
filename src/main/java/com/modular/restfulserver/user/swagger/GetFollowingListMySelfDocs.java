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
  summary = "본인 팔로잉 리스트 조회 API",
  description = "요청한 사용자의 팔로잉 리스트를 반환합니다.",
  tags = SwaggerMessageUtils.FollowApi,
  security = { @SecurityRequirement(name = "bearer-key") }
)
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation =
    FollowSchemas.FollowUserSchema.class
  ))),
})
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface GetFollowingListMySelfDocs {}
