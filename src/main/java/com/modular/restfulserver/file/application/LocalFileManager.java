package com.modular.restfulserver.file.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class LocalFileManager implements FileManageable {

  private final String serverUrl;
  private final String uploadDir;

  public LocalFileManager(
    @Value("${server.port}") final String port,
    @Value("${server.address}") final String address,
    @Value("${server.protocol}") final String protocol,
    @Value("${custom.path.upload-images}") final String uploadDir
  ) throws IOException {
    this.serverUrl = protocol + "://" + address + ":" + port;
    this.uploadDir = uploadDir;
    createUploadDirectoryIfNotExists();
  }

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

  private void createUploadDirectoryIfNotExists() throws IOException {
    File existsFile = new File(this.uploadDir);
    if (!existsFile.isDirectory() || !existsFile.exists())
      Files.createDirectory(Paths.get(this.uploadDir));
  }

}
