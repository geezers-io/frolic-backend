package com.modular.restfulserver.article.application;

import org.springframework.web.multipart.MultipartFile;

public interface FileManager {

  void singleFileUpload(MultipartFile file);

  void multipleFileUpload(MultipartFile[] files);

  byte[] download(String fileKey);

}
