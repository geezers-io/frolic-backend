package com.frolic.sns.global.common.file.api;

import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.common.file.application.FileManageable;
import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/api/file")
@RequiredArgsConstructor
@RestController
public class UploadApi {

  private final FileManageable fileManager;
  private final JwtProvider jwtProvider;

  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Map<String, FileInfo>> uploadFileApi(
    @RequestPart MultipartFile file,
    HttpServletRequest request
  ) {
    FileInfo fileInfo = fileManager.singleUpload(file, jwtProvider.getTokenByHttpRequestHeader(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseHelper.createDataMap(fileInfo));
  }

}
