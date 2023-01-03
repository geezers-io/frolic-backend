package com.modular.restfulserver.auth.swagger;

import com.modular.restfulserver.global.swagger.CommonAuthError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "로그아웃 요청",
  description = "인증 사용자의 검증 토큰을 서버 보관소에서 삭제합니다.",
  tags = "인증 및 인가 API",
  security = { @SecurityRequirement(name = "bearer-key") }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogoutDocs {}
