package com.modular.restfulserver.article.swagger;

import com.modular.restfulserver.global.swagger.CommonAuthError;
import com.modular.restfulserver.global.swagger.SwaggerCommonErrorSchema;
import com.modular.restfulserver.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "사용자 댓글 목록 조회 API",
  description = "사용자의 모든 댓글 정보를 조회합니다.",
  tags = SwaggerMessageUtils.CommentApi,
  security = { @SecurityRequirement(name = "bearer-key") },
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "OK",
      content = @Content(schema = @Schema(implementation = CommentSchemas.CommentListSchema.class ))
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Not Found",
      content = @Content(schema = @Schema(implementation = SwaggerCommonErrorSchema.class))
    )
  }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface GetCommentListByUsernameDocs { }
