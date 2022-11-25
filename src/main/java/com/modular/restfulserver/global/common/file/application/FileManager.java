package com.modular.restfulserver.global.common.file.application;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileManager {

  void singleFileUpload(MultipartFile file);

  void multipleFileUpload(List<MultipartFile> files);

  byte[] download(String fileKey);

}
