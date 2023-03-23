package com.frolic.sns.global.common.file.application;

import com.frolic.sns.global.common.file.exception.FaultFileExtensionException;
import com.frolic.sns.global.common.file.exception.FaultFilenameException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public interface FileExtensionValidator {

  List<String> imageExtensionNameList = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "svg", "webp");

  default String getExtensionName(MultipartFile file) {
    String filename = file.getOriginalFilename();
    if (filename == null) throw new FaultFilenameException();

    int extIndex = file.getOriginalFilename().indexOf(".");
    if (extIndex == -1) throw new FaultFileExtensionException();

    return file.getOriginalFilename().substring(extIndex).substring(1);
  }

}
