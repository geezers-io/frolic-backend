package com.frolic.sns.global.common.file.application;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Component
public final class ImageFileExtensionValidator implements FileExtensionValidator {

  private final List<String> extensionNameList = Arrays.asList("jpg", ".jpeg", ".png", "gif", "bmp", "svg", "webp");

  public void validate(MultipartFile file) {
    if (file == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 파일 형식이 잘못되었습니다.");
    String extensionName = getExtensionName(file);
    if (!extensionNameList.contains(extensionName)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지 파일 확장자가 아닙니다.");
    }
  }

}
