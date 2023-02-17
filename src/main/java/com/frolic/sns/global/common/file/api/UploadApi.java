package com.frolic.sns.global.common.file.api;

import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.common.file.application.FileManageable;
import com.frolic.sns.global.common.file.dto.FileInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/api/file")
@RestController
public class UploadApi {

  private final FileManageable fileManager;

  public UploadApi(@Qualifier("LocalFileManager") FileManageable fileManager) {
    this.fileManager = fileManager;
  }

  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Map<String, FileInfo>> uploadFileApi(@RequestPart MultipartFile file) {
    FileInfo fileInfo = fileManager.singleUpload(file);
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseHelper.createDataMap(fileInfo));
  }

}
