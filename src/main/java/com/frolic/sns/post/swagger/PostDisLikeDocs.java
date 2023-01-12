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
  summary = "게시글 좋아요 취소 API",
  description = "요청한 사용자와 게시글 간 좋아요 관계를 제거합니다.",
  tags = SwaggerMessageUtils.ArticleManagementApi,
  security = { @SecurityRequirement(name = "bearer-key") },
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "OK",
      content = @Content(schema = @Schema(implementation = PostSchemas.LikeSchema.class ))
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Not Found",
      content = @Content(schema = @Schema(implementation = SwaggerCommonErrorSchema.class ))
    )
  }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PostDisLikeDocs { }
