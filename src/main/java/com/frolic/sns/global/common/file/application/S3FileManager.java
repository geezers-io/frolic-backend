package com.frolic.sns.global.common.file.application;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class S3FileManager implements FileManageable {

  private final FileRepository fileRepository;

  @Override
  public FileInfo singleUpload(MultipartFile file, String token) {
    return null;
  }

  @Override
  public List<FileInfo> multipleUpload(List<MultipartFile> files, String token) {
    return null;
  }

  @Override
  public UrlResource download(String filename) {
    return null;
  }
}