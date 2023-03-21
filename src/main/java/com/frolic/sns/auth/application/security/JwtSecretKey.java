package com.frolic.sns.auth.application.security;

import com.frolic.sns.auth.config.JwtProperties;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@Getter
public class JwtSecretKey {

  private final JwtProperties jwtProperties;
  private final Key key;

  public JwtSecretKey(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
  }
}
