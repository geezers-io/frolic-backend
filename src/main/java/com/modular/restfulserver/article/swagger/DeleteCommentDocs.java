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
  summary = "댓글 삭제 API",
  description = "댓글을 삭제합니다.",
  tags = SwaggerMessageUtils.ArticleManagementApi,
  security = { @SecurityRequirement(name = "bearer-key") },
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "OK"
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
public @interface DeleteCommentDocs { }
