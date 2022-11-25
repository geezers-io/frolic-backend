package com.modular.restfulserver.global.common.file.application;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileManager {

  void singleFileUpload(CustomFile file);

  void multipleFileUpload(List<CustomFile> files);

  byte[] download(String fileKey);

}
