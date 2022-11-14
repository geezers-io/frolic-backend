package com.modular.restfulserver.global.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

  private final Key jwtSecretKey;
  private final Long accessTokenExpTime;
  private final Long refreshTokenExpTime;


  public JwtProvider(
    @Value("${jwt.secret}") String registeredSecretKey,
    @Value("${jwt.accessTokenExpTime}") Long accessTokenExpTime,
    @Value("${jwt.refreshTokenExpTime}") Long refreshTokenExpTime

    ) {
    byte[] keyBytes = Decoders.BASE64.decode(registeredSecretKey);
    this.jwtSecretKey = Keys.hmacShaKeyFor(keyBytes);
    this.accessTokenExpTime = accessTokenExpTime;
    this.refreshTokenExpTime = refreshTokenExpTime;
  }

  public String getTokenByHttpRequestHeader(HttpServletRequest request) {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null) return null;
    return authorizationHeader
      .substring(JwtConstants.TOKEN_HEADER_PREFIX.length());
  }

  public String createAccessToken(String email) {
    Claims claims = Jwts.claims().setSubject(email);
    return createToken(claims, accessTokenExpTime);
  }

  public String createRefreshToken(String email) {
    Claims claims = Jwts.claims().setSubject(email);
    return createToken(claims, refreshTokenExpTime);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(jwtSecretKey)
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (JwtException ex) {
      log.error(ex.getMessage());
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts
      .parserBuilder()
      .setSigningKey(jwtSecretKey)
      .build()
      .parseClaimsJws(token)
      .getBody();

    return new CustomEmailPasswordAuthToken(claims.getSubject());
  }

  public String getUserEmailByToken(String token) {
    return parseClaims(token).getSubject();
  }

  private String createToken(Claims claims, long expTime) {
    return Jwts.builder()
      .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
      .setClaims(claims)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + expTime))
      .compact();
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder()
        .setSigningKey(jwtSecretKey)
        .build()
        .parseClaimsJws(accessToken)
        .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

}
