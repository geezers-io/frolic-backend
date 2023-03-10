package com.frolic.sns.post.swagger;

import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "피드 삭제 API",
  description = "피드를 삭제합니다.",
  tags = SwaggerMessageUtils.ArticleManagementApi,
  security = { @SecurityRequirement(name = "bearer-key") },
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Not Found"
    )
  }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DeletePostDocs { }
