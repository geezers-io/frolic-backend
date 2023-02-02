package com.frolic.sns.global.common.file.config;

import com.frolic.sns.global.common.file.application.FileManageable;
import com.frolic.sns.global.common.file.application.LocalFileManager;
import com.frolic.sns.global.common.file.application.S3FileManager;
import com.frolic.sns.global.common.file.repository.FileRepository;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FIleManagerConfiguration {
  private final FileRepository fileRepository;

  private final UserRepository userRepository;

  private final JwtProvider jwtProvider;

  @Value("${spring.profiles.active}")
  private String profile;

  public FileManageable localFileManager() {
    log.error("profile: {}", profile);
    if (profile.equals("local")) {
      log.info("파일 관리자 객체가 LocalFileManager 로 생성되었습니다.");
      return new LocalFileManager(fileRepository, userRepository, jwtProvider);
    } else {
      log.info("파일 관리자 객체가 S3FileManager 로 생성되었습니다.");
      return new S3FileManager(fileRepository);
    }
  }

}
