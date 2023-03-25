package com.frolic.sns.auth.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@ConfigurationProperties(prefix = "mail")
public class EmailManagerProperties {
  private String host;
  private String port;
  private String username;
  private String password;
}
