package com.frolic.sns.global.common.file.application;

import com.frolic.sns.global.common.file.exception.FaultFileExtensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileExtensionTest {

  @Autowired
  protected LocalFileManager localFileManager;

  String correctFilename = "galaxy4276.jpg";

  String inCorrectFilename = "galaxy4276";

  @Test
  @DisplayName("파일이름을 성공적으로 변조한다.")
  void createCorrectTemperedFilename() {
    String name = localFileManager.getTemperedFilename(correctFilename);
    System.out.println("name: " + name);
  }

  @Test
  @DisplayName("확장자가 없는 파일은 변조에 실패한다.")
  void createInCorrectTemperedFilename() {
    assertThrows(FaultFileExtensionException.class, () -> {
      localFileManager.getTemperedFilename(inCorrectFilename);
    });
  }

}