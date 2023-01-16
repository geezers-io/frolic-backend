package com.frolic.sns.auth.swagger;

import com.frolic.sns.global.swagger.CommonAuthError;
import com.frolic.sns.global.swagger.SwaggerMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Operation(
  summary = "SMS 인증번호 발송 Api",
  description = "SMS로 인증번호를 발송합니다.",
  tags = SwaggerMessageUtils.AuthApi,
  security = { @SecurityRequirement(name = "bearer-key") }
)
@CommonAuthError
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SendSMSDocs {}
