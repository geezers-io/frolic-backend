package com.frolic.sns.auth.application.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void commence(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException authException
  ) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

    Map<String, String> resData = new HashMap<>();
    if (bearerToken == null) {
      resData.put("error", "헤더에 토큰이 존재하지 않습니다.");
      String resDataString = mapping(resData);
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getWriter().write(resDataString);
      return;
    }
    resData.put("error", "토큰이 유효하지 않습니다.");
    String resDataString = mapping(resData);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(resDataString);
  }

  private String mapping(Map<String, String> data) throws JsonProcessingException {
    return mapper.writeValueAsString(data);
  }
}
