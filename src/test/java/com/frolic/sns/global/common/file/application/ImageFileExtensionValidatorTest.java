package com.frolic.sns.global.common.file.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class ImageFileExtensionValidatorTest {

  private final ImageFileExtensionValidator imageFileExtensionValidator = new ImageFileExtensionValidator();

  @Test
  @DisplayName("이미지 파일이 아니라면 예외가 발생한다.")
  void invalidFileExtension() {
    // given
    MockMultipartFile mockMultipartFile = new MockMultipartFile("test.exe", "test.exe", "image", "hello".getBytes());

    // when, then
    assertThrows(ResponseStatusException.class, () -> {
      imageFileExtensionValidator.validate(mockMultipartFile);
    });
  }

  @Test
  @DisplayName("이미지 파일이 정상 검수된다.")
  void validFileExtension() {
    // given
    MockMultipartFile mockMultipartFile = new MockMultipartFile("test.svg", "test.svg", "image", "hello".getBytes());

    // when, then
    assertDoesNotThrow(() -> imageFileExtensionValidator.validate(mockMultipartFile));
  }

}
