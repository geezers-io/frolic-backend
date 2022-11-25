package com.modular.restfulserver.global.common.file.application;

import com.modular.restfulserver.global.common.file.exception.FaultFileExtensionException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class CustomFile {
  private final MultipartFile file;
  private final String customFilename;

  public CustomFile(MultipartFile file) {
    this.file = file;
    String filename = file.getOriginalFilename();
    // TODO: 2022-11-25 확장자가 잘못된 형식에 대해 예외 처리 필요 
    int splitIndex = filename.indexOf(".");
    if (splitIndex == -1)
      throw new FaultFileExtensionException();
    String ext = filename.substring(splitIndex);
    this.customFilename = filename.substring(0, splitIndex) + "_" + UUID.randomUUID() + ext;
  }

}
