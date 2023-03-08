package com.frolic.sns.global.common.file.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.frolic.sns.global.common.file.application.FileManager;
import com.frolic.sns.global.common.file.application.LocalFileManager;
import com.frolic.sns.global.common.file.application.S3FileManager;
import com.frolic.sns.global.common.file.repository.FileRepository;
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

  private final LocalFileProperties localFileProperties;
  private final FileRepository fileRepository;
  private final AmazonS3Client s3Client;

  @Bean
  public FileManager fileManager() {
    if (applicationEnv.equals("local")) {
      log.info("LocalFileManager Initialized!");
      return new LocalFileManager(fileRepository, localFileProperties);
    }
    if (applicationEnv.equals("prod")) {
      log.info("S3FileManager Initialized!");
      return new S3FileManager(fileRepository, s3Client);
    }
    throw new RuntimeException("fileManager 초기화 실패");
  }

}
