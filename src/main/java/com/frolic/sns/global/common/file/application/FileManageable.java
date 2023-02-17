package com.frolic.sns.global.common.file.application;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.file.exception.FaultFileExtensionException;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface FileManageable {
  FileInfo singleUpload(MultipartFile file);

  List<FileInfo> multipleUpload(List<MultipartFile> files);

  UrlResource download(String filename);

  default String getTemperedFilename(String filename) {
    int extIndex = filename.indexOf(".");
    if (extIndex == -1) throw new FaultFileExtensionException();
    String ext = filename.substring(extIndex);
    String name = filename.substring(0, extIndex);
    return name + "_" + UUID.randomUUID() + ext;
  }

  default String getExtensionName(MultipartFile file) {
    int extIndex = Objects.requireNonNull(file.getOriginalFilename()).indexOf(".");
    if (extIndex == -1) throw new FaultFileExtensionException();
    return file.getOriginalFilename().substring(extIndex);
  }

}
