package com.frolic.sns.global.common.file.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class LocalFileProperties {
  private final String uploadDirPath;

  private final String host;

  private final String port;


  public LocalFileProperties(
          @Value("${system.path.upload-images}") String uploadDirPath,
          @Value("${server.address}") String host,
          @Value("${server.port}") String port) {
    this.uploadDirPath = uploadDirPath;
    this.host = host;
    this.port = port;
  }

}
