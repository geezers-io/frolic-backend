package com.frolic.sns.auth.config;

import com.frolic.sns.auth.application.security.TokenType;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@Getter
public class JwtConfig {

  private final Key secretKey;
  private final Long accessTokenExpTime;
  private final Long refreshTokenExpTime;

  public JwtConfig(
    @Value("${jwt.secret}") String registeredSecretKey,
    @Value("${jwt.accessTokenExpTime}") Long accessTokenExpTime,
    @Value("${jwt.refreshTokenExpTime}") Long refreshTokenExpTime
  ) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(registeredSecretKey));
    this.accessTokenExpTime = accessTokenExpTime;
    this.refreshTokenExpTime = refreshTokenExpTime;
  }

  public Long getExpTime(TokenType tokenType) {
    if (tokenType.equals(TokenType.ACCESS_TOKEN)) return accessTokenExpTime;
    if (tokenType.equals(TokenType.REFRESH_TOKEN)) return refreshTokenExpTime;
    throw new NullPointerException("token 값이 null 입니다.");
  }

}
