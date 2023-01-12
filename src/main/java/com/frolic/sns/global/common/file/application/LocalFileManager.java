package com.frolic.sns.global.common.file.application;

import com.frolic.sns.global.common.file.exception.FileDownloadFailureException;
import com.frolic.sns.global.common.file.exception.FileSaveFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class LocalFileManager implements FileManageable {

  private final String uploadDir;

  public LocalFileManager(
    @Value("${custom.path.upload-images}") final String uploadDir
  ) throws IOException {
    this.uploadDir = uploadDir;
    createUploadDirectory();
  }

  @Override
  public void singleUpload(MultipartFile file) {
    store(file);
  }

  @Override
  public void multipleUpload(List<MultipartFile> files) {
    files.forEach(this::store);
  }

  @Override
  public UrlResource download(String filename) {
    String path = uploadDir + "/" + filename;
    try {
      return new UrlResource(path);
    } catch (MalformedURLException exception) {
      throw new FileDownloadFailureException();
    }
  }

  private void createUploadDirectory() throws IOException {
    File existsFile = new File(this.uploadDir);
    if (!existsFile.isDirectory() || !existsFile.exists())
      Files.createDirectory(Paths.get(this.uploadDir));
  }

  private void store(MultipartFile file) {
    String filename = Objects.requireNonNull(file.getOriginalFilename());
    String temperedFilename = getTemperedFilename(filename);
    try (InputStream inputStream = file.getInputStream()) {
      Path updateDirPath = Paths.get(uploadDir + "/" + temperedFilename);
      Files.copy(inputStream, updateDirPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception ex) {
      log.error(ex.toString());
      throw new FileSaveFailException();
    }
  }

}
