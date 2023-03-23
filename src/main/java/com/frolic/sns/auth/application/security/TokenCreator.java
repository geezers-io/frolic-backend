package com.frolic.sns.auth.application.security;

import com.frolic.sns.auth.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class TokenCreator {

  private final JwtProperties jwtProperties;
  private final JwtSecretKey jwtSecretKey;

  public String createToken(String email, TokenType tokenType) {
    return create(email, getExpTime(tokenType), tokenType);
  }

  private Map<String, String> createClaims(String userEmail, TokenType tokenType) {
    Map<String, String> claims = new HashMap<>();
    claims.put(TokenKey.USER_EMAIL.name(), userEmail);

    if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
      claims.put(TokenKey.TOKEN_TYPE.name(), TokenType.ACCESS_TOKEN.name());
    }
    if (tokenType.equals(TokenType.REFRESH_TOKEN)) {
      claims.put(TokenKey.TOKEN_TYPE.name(), TokenType.REFRESH_TOKEN.name());
    }

    return claims;
  }

  private String create(String userEmail, Long exp, TokenType tokenType) {
    return Jwts.builder()
      .signWith(jwtSecretKey.getKey(), SignatureAlgorithm.HS256)
      .setClaims(createClaims(userEmail, tokenType))
      .setIssuedAt(new Date())
      .setExpiration(createTokenExpTime(exp))
      .compact();
  }

  private Date createTokenExpTime(Long exp) {
    return new Date(System.currentTimeMillis() + exp);
  }

  private Long getExpTime(TokenType tokenType) {
    if (tokenType.equals(TokenType.ACCESS_TOKEN))
      return jwtProperties.getAccessTokenExpTime();
    if (tokenType.equals(TokenType.REFRESH_TOKEN))
      return jwtProperties.getRefreshTokenExpTime();
    throw new NullPointerException("tokenType argument must be not null");
  }

}
