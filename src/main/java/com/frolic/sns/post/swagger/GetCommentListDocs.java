package com.frolic.sns.post.swagger;

import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerCommonErrorSchema;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "게시글 댓글 목록 조회 API",
  description = "해당 게시글의 모든 댓글을 조회합니다.",
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
public @interface GetCommentListDocs { }
