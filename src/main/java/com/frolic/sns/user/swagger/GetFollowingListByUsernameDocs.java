package com.frolic.sns.user.swagger;

import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "사용자 팔로잉 리스트 조회 API",
  description = "요청한 사용자 이름 정보로 해당 사용자의 팔로잉 리스트를 반환합니다.",
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
public @interface GetFollowingListByUsernameDocs {}
