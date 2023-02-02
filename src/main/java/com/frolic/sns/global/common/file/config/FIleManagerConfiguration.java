package com.frolic.sns.global.common.file.config;

import com.frolic.sns.global.common.file.application.FileManageable;
import com.frolic.sns.global.common.file.application.LocalFileManager;
import com.frolic.sns.global.common.file.application.S3FileManager;
import com.frolic.sns.global.common.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FIleManagerConfiguration {
  private final FileRepository fileRepository;

  @Value("${spring.profiles.active}")
  private String profile;

  public FileManageable localFileManager() {
    log.error("profile: {}", profile);
    if (profile.equals("local")) {
      log.info("파일 관리자 객체가 LocalFileManager 로 생성되었습니다.");
      return new LocalFileManager(fileRepository);
    } else {
      log.info("파일 관리자 객체가 S3FileManager 로 생성되었습니다.");
      return new S3FileManager(fileRepository);
    }
  }

}
