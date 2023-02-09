package com.frolic.sns.global.common.file.application;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.file.exception.FileDownloadFailureException;
import com.frolic.sns.global.common.file.exception.FileSaveFailException;
import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.global.common.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
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
import java.util.stream.Collectors;

@Qualifier("LocalFileManager")
@Service
@Slf4j
@RequiredArgsConstructor
public class LocalFileManager implements FileManageable {

  private final FileRepository fileRepository;

  @Value("${system.path.upload-images}")
  private String uploadDir;

  @Value("${server.address}")
  private String host;

  @Value("${server.port}")
  private String port;

  @Value("${system.protocol}")
  private String protocol;

  @PostConstruct
  public void postConstruct() throws IOException {
    createUploadDirectory();
  }

  @Override
  public FileInfo singleUpload(MultipartFile file) {
    return store(file);
  }

  @Override
  public List<FileInfo> multipleUpload(List<MultipartFile> files) {
    return files.stream().map(this::store).collect(Collectors.toList());
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

  private FileInfo store(MultipartFile file) {
    String filename = Objects.requireNonNull(file.getOriginalFilename());
    String temperedFilename = getTemperedFilename(filename);
    return createFile(file, temperedFilename);
  }

  private FileInfo createFile(MultipartFile file, String temperedName) {
    try (InputStream inputStream = file.getInputStream()) {
      Path updateDirPath = Paths.get(uploadDir + "/" + temperedName);
      Files.copy(inputStream, updateDirPath, StandardCopyOption.REPLACE_EXISTING);
      return FileInfo.from(createFileModel(file, temperedName));
    } catch (Exception ex) {
      log.error(ex.toString());
      throw new FileSaveFailException();
    }
  }

  private ApplicationFile createFileModel(MultipartFile file, String name) {
    ApplicationFile applicationFile = ApplicationFile.builder()
      .addName(name)
      .addSize(file.getSize())
      .addDownloadUrl(protocol + "://" + host + ":" + port + "/images/" + name)
      .build();
    return fileRepository.saveAndFlush(applicationFile);
  }

}
