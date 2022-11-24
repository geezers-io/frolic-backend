package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.exception.FaultFilenameException;
import com.modular.restfulserver.article.exception.FileSaveFailException;
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
  public void singleFileUpload(MultipartFile file) {
    store(file);
  }

  @Override
  public void multipleFileUpload(List<MultipartFile> files) {
    for (var file : files)
      store(file);
  }

  @Override
  public byte[] download(String fileKey) {
    return new byte[0];
  }

  private void store(MultipartFile file) {
    if (Objects.isNull(file.getOriginalFilename()))
      throw new FaultFilenameException();

    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, uploadDirPath.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception ex) {
      log.error(ex.toString());
      throw new FileSaveFailException();
    }
  }

}
