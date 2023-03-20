package com.frolic.sns.auth.application.security;

import com.frolic.sns.auth.config.JwtProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final JwtProperties jwtProperties;
  private final JwtSecretKey jwtSecretKey;

  public String getTokenByHttpRequestHeader(HttpServletRequest request) {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null) return null;
    return authorizationHeader.substring(JwtConstants.TOKEN_HEADER_PREFIX.length());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(jwtSecretKey.getKey())
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (JwtException ex) {
      log.error(ex.getMessage());
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    Claims claims = parseClaims(token);
    String userEmail = (String) claims.get(TokenKey.USER_EMAIL.name());
    return new FrolicAuthenticationToken(userEmail, token);
  }

  public String getUserEmailByToken(String token) {
    Claims claims = parseClaims(token);
    return (String) claims.get(TokenKey.USER_EMAIL.name());
  }

  public Claims parseClaims(String token) {
    try {
      return Jwts.parserBuilder()
        .setSigningKey(jwtSecretKey.getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

}
