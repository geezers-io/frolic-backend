package com.modular.restfulserver.file.application;

import com.modular.restfulserver.file.exception.FaultFileExtensionException;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileManageable {
  void singleUpload(MultipartFile file);

  void multipleUpload(List<MultipartFile> files);

  UrlResource download(String filename);

  default String getTemperedFilename(String filename) {
    int extIndex = filename.indexOf(".");
    if (extIndex == -1) throw new FaultFileExtensionException();
    String ext = filename.substring(extIndex);
    String name = filename.substring(0, extIndex);
    return name + "_" + UUID.randomUUID() + ext;
  }

}
