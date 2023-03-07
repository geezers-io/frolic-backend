package com.frolic.sns.global.common.file.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.file.exception.FaultFilenameException;
import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.global.common.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Qualifier("S3FileManager")
@Service
@RequiredArgsConstructor
public class S3FileManager implements FileManageable {

  private final FileRepository fileRepository;
  private final AmazonS3Client s3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  @Override
  public FileInfo singleUpload(MultipartFile multipartFile) {
    if (multipartFile.getOriginalFilename() == null) throw new FaultFilenameException();
    String temperedFilename = getTemperedFilename(multipartFile.getOriginalFilename());
    String downloadUrl = "willbehere"; // TODO: check

    ApplicationFile applicationFile = ApplicationFile.builder()
      .addName(temperedFilename)
      .addSize(multipartFile.getSize())
      .addDownloadUrl(downloadUrl)
      .build();
    fileRepository.saveAndFlush(applicationFile);

    File newFile = createFile(multipartFile);
    s3Client.putObject(
      new PutObjectRequest(bucketName, temperedFilename, newFile)
        .withCannedAcl(CannedAccessControlList.PublicRead)
    );

    return FileInfo.builder()
      .addId(applicationFile.getId())
      .addFilename(temperedFilename)
      .addDownloadUrl(downloadUrl)
      .build();
  }

  @Override
  public List<FileInfo> multipleUpload(List<MultipartFile> files) {
    return files.stream().map(this::singleUpload).collect(Collectors.toList());
  }

  public S3ObjectInputStream download(String filename) {
    return s3Client.getObject(bucketName, filename).getObjectContent();
  }

  private File createFile(MultipartFile multipartFile) {
    File file;

    try {
      file = new File(multipartFile.getInputStream().toString());
    } catch (IOException e) {
      throw new ServerErrorException("파일 업로드가 실패하였습니다." + e.toString());
    }

    return file;
  }

}
