package com.modular.restfulserver.global.common.file.application;

import com.modular.restfulserver.global.common.file.exception.FaultFilenameException;
import com.modular.restfulserver.global.common.file.exception.FileDownloadFailureException;
import com.modular.restfulserver.global.common.file.exception.FileSaveFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FileManagerImpl implements FileManager {

  private final Path uploadDirPath;

  public FileManagerImpl() {
    this.uploadDirPath = Paths.get(System.getProperty("user.dir") + "/uploads");
    try {
      if (!Files.exists(uploadDirPath))
        Files.createDirectories(uploadDirPath);
    } catch (IOException ex) {
      throw new RuntimeException("uploadPath 에 디렉터리를 생성할 수 없습니다.");
    }
  }

  @Override
  public void singleFileUpload(CustomFile file) {
    store(file);
  }

  @Override
  public void multipleFileUpload(List<CustomFile> files) {
    files.forEach(this::store);
  }

  @Override
  public byte[] download(String fileKey) {
    Path filePath = Paths.get(this.uploadDirPath + "/" + fileKey);
    try {
      return Files.readAllBytes(filePath);
    } catch (IOException ex) {
      log.error(ex.getMessage());
      throw new FileDownloadFailureException();
    }
  }

  private void store(CustomFile file) {
    if (Objects.isNull(file.getCustomFilename()))
      throw new FaultFilenameException();

    try (InputStream inputStream = file.getFile().getInputStream()) {
      Files.copy(inputStream, uploadDirPath.resolve(file.getCustomFilename()), StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception ex) {
      log.error(ex.toString());
      throw new FileSaveFailException();
    }
  }

}
