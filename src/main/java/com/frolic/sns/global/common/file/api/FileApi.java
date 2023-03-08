package com.frolic.sns.global.common.file.api;

import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.common.file.application.FileManager;
import com.frolic.sns.global.common.file.dto.FileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/api/file")
@RestController
@RequiredArgsConstructor
public class FileApi {

  private final FileManager fileManager;

  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Map<String, FileInfo>> uploadFileApi(@RequestPart("image") MultipartFile file) {
    FileInfo fileInfo = fileManager.singleUpload(file);
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseHelper.createDataMap(fileInfo));
  }

  @GetMapping("/{filename}")
  public ResponseEntity<byte[]> downloadFileApi(@PathVariable("filename") String filename) {
    byte[] fileBytes = fileManager.download(filename);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
  }

}
