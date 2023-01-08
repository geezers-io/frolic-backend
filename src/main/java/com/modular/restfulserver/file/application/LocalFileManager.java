package com.modular.restfulserver.file.application;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class LocalFileManager implements FileManageable {
  @Override
  public void singleUpload(MultipartFile file) {

  }

  @Override
  public void multipleUpload(List<MultipartFile> files) {

  }

  @Override
  public UrlResource download(String filename) {
    return null;
  }

}
