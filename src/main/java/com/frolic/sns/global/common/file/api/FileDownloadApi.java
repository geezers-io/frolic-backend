package com.frolic.sns.global.common.file.api;

import com.frolic.sns.global.common.file.application.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class FileDownloadApi {

  private final FileManager fileManager;

  @GetMapping("/{filename}")
  public ResponseEntity<byte[]> getFileApi(@PathVariable String filename) {
    return ResponseEntity.ok(fileManager.download(filename));
  }

}
