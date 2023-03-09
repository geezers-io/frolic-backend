package com.frolic.sns.global.common.file.api.swagger;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "이미지 파일 다운로드 API",
  description = "파일 이름 인자를 통해 이미지 파일을 다운 받습니다.",
  tags = SwaggerMessageUtils.fileApi
)
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DownloadFileDocs { }
