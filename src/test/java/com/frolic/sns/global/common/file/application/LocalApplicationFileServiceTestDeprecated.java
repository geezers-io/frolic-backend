package com.frolic.sns.global.common.file.application;

import com.frolic.sns.global.common.file.exception.FaultFileExtensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocalApplicationFileServiceTestDeprecated {

  @Autowired
  protected LocalFileService localFileManager;

  private final byte[] fileContentBytes = "Hello, World".getBytes();

  @Test
  @DisplayName("확장자가 없는 파일 업로드는 실패한다.")
  void failedNoExtensionFile() {
    MultipartFile file = new MockMultipartFile(
      "foo",
      "maybe_image",
      MediaType.TEXT_PLAIN_VALUE,
      fileContentBytes
    );
    assertThrows(FaultFileExtensionException.class, () -> localFileManager.uploadSingleFile(file));
  }

//  @Test
//  @DisplayName("확장자가 존재하는 파일 업로드는 성공한다.")
//  void successFileUpload() {
//    MultipartFile file = new MockMultipartFile(
//      "foo",
//      "foo.txt",
//      MediaType.TEXT_PLAIN_VALUE,
//      fileContentBytes
//    );
//    assertDoesNotThrow(() -> localFileManager.singleUpload(file));
//  }

}