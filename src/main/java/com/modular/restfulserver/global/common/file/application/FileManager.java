package com.modular.restfulserver.global.common.file.application;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface FileManager {

  void singleFileUpload(CustomFile file);

  void multipleFileUpload(List<CustomFile> files);

  byte[] download(String fileKey);

  static List<CustomFile> createCustomFileList(List<MultipartFile> multipartFiles) {
    if (multipartFiles.get(0).isEmpty()) return new ArrayList<>();
    return multipartFiles.stream().map(CustomFile::new).collect(Collectors.toList());
  }

}
