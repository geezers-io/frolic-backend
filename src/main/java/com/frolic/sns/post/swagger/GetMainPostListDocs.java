package com.frolic.sns.post.swagger;

import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "피드 목록 API",
  description = "전체 피드 목록을 최신순으로 조회하여 반환합니다.",
  tags = SwaggerMessageUtils.ArticleManagementApi,
  security = { @SecurityRequirement(name = "bearer-key") },
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "OK",
      content = @Content(schema = @Schema(implementation = PostSchemas.CreateArticleSchema.class ))
    ),
  }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface GetMainPostListDocs { }
