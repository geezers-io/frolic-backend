package com.frolic.sns.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
  private String secret;
  private Long accessTokenExpTime;
  private Long refreshTokenExpTime;
}
