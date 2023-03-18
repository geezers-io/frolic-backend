package com.frolic.sns.auth.application.security;

import com.frolic.sns.auth.config.JwtConfig;
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

  private final JwtConfig jwtConfig;

  public String createToken(String email, TokenType tokenType) {
    return create(email, jwtConfig.getExpTime(tokenType), tokenType);
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
      .signWith(jwtConfig.getSecretKey(), SignatureAlgorithm.HS256)
      .setClaims(createClaims(userEmail, tokenType))
      .setIssuedAt(new Date())
      .setExpiration(createTokenExpTime(exp))
      .compact();
  }

  private Date createTokenExpTime(Long exp) {
    return new Date(System.currentTimeMillis() + exp);
  }

}
