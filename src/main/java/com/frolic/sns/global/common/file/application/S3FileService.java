package com.frolic.sns.global.common.file.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.file.exception.FaultFilenameException;
import com.frolic.sns.global.common.file.exception.FileDownloadFailureException;
import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.global.common.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Qualifier("S3FileManager")
@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileService implements FileService {

  private final FileRepository fileRepository;
  private final AmazonS3Client s3Client;

  private final FileManager fileManager;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  @Override
  public FileInfo uploadSingleFile(MultipartFile multipartFile) {
    ApplicationFile applicationFile = createApplicationFile(multipartFile);
    File newFile = createFile(multipartFile, applicationFile.getName());
    PutObjectRequest objectRequest = new PutObjectRequest(bucketName, applicationFile.getName(), newFile)
      .withCannedAcl(CannedAccessControlList.PublicRead);
    s3Client.putObject(objectRequest);

    return FileInfo.from(applicationFile);
  }

  @Override
  public List<FileInfo> uploadMultipleFile(List<MultipartFile> files) {
    return files.stream().map(this::uploadSingleFile).collect(Collectors.toList());
  }

  @Override
  public byte[] download(String filename) {
    ApplicationFile applicationFile = fileManager.getFileByName(filename);
    try (S3ObjectInputStream stream = s3Client.getObject(bucketName, applicationFile.getName()).getObjectContent()) {
      return stream.readAllBytes();
    } catch (IOException e) {
      throw new FileDownloadFailureException();
    }
  }

  private ApplicationFile createApplicationFile(MultipartFile multipartFile) {
    if (multipartFile.getOriginalFilename() == null) throw new FaultFilenameException();
    ApplicationFile applicationFile = ApplicationFile.builder()
      .addName(getTemperedFilename(multipartFile.getOriginalFilename()))
      .addSize(multipartFile.getSize())
      .build();
    return fileRepository.saveAndFlush(applicationFile);
  }

  private File createFile(MultipartFile multipartFile, String temperedName) {
    File file = new File(temperedName);
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      fileOutputStream.write(multipartFile.getBytes());
      return file;
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드가 실패하였습니다." + e.toString());
    }
  }

}
