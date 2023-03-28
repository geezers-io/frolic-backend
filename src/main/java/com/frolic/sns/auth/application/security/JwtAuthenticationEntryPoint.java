package com.frolic.sns.auth.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,  AuthenticationException authException) throws IOException {
    log.error(authException.getMessage());
    sendErrorResponse(response, "인증 권한이 없습니다.");
  }

  private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
    response.setCharacterEncoding("utf-8");
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    Map<String, String> data = new HashMap<>();
    data.put("error", message);
    response.getWriter().write(objectMapper.writeValueAsString(data));
  }

}
