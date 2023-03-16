package com.frolic.sns.post.swagger;

import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Operation(
  summary = "해시태그 검색 기반 게시글 불러오기 API",
  description = "해시태그 리스트와 cursorId 값 기반으로 게시글을 불러옵니다.",
  tags = SwaggerMessageUtils.ArticleManagementApi,
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "게시글을 성공적으로 불러옵니다.",
      content = @Content(schema = @Schema(implementation = PostSchemas.GetPostListSchema.class))
    )
  }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface GetPostsByHashtagsDocs { }
