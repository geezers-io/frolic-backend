package com.frolic.sns.global.common.file.config;

import com.frolic.sns.global.common.file.application.FileService;
import com.frolic.sns.global.common.file.application.LocalFileService;
import com.frolic.sns.global.common.file.application.S3FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FileManagerConfig {

  @Value("${spring.profiles.active}")
  private String applicationEnv;

  private final LocalFileService localFileService;

  private final S3FileService s3FileService;

  @Bean
  public FileService fileService() {
    if (applicationEnv.equals("local")) {
      log.info("LocalFileService Initialized!");
      return localFileService;
    }
    if (applicationEnv.equals("prod")) {
      log.info("S3FileService Initialized!");
      return s3FileService;
    }
    throw new RuntimeException("fileManager 초기화 실패");
  }

}
