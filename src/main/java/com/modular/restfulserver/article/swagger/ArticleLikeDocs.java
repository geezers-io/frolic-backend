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
  summary = "게시글 좋아요 API",
  description = "요청한 사용자와 게시글 간 좋아요 관계를 생성합니다.",
  tags = SwaggerMessageUtils.ArticleManagementApi,
  security = { @SecurityRequirement(name = "bearer-key") },
  responses = {
    @ApiResponse(
      responseCode = "201",
      description = "Created",
      content = @Content(schema = @Schema(implementation = ArticleSchemas.LikeSchema.class ))
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
public @interface ArticleLikeDocs { }
