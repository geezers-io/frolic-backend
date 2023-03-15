package com.frolic.sns.post.swagger;

import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Operation(
  summary = "사용자 게시글 리스트 불러오기 API",
  description = "cursorId 값 기반으로 요청 사용자의 게시글 리스트를 불러옵니다.",
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
public @interface GetUserPostsDocs { }
