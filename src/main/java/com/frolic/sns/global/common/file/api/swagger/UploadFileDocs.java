package com.frolic.sns.global.common.file.api.swagger;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "이미지 파일 업로드 API",
  description = "이미지 파일인 지 확장자를 확인하고 서버 내에 정보를 저장합니다.",
  tags = SwaggerMessageUtils.fileApi,
  security = { @SecurityRequirement(name = "bearer-key") },
  responses = {
    @ApiResponse(
      responseCode = "200",
      description = "업로드 성공",
      content = @Content(schema = @Schema(implementation = FileInfo.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "이미지 확장자가 아님"
    ),
  }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UploadFileDocs { }
