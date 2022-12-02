package com.modular.restfulserver.global.common.file.api;

import com.modular.restfulserver.global.common.file.application.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("download")
@RequiredArgsConstructor
public class FileDownloadApi {

  private final FileManager fileManager;

  @GetMapping("/{filename}")
  public ResponseEntity<byte[]> getFileApi(@PathVariable String filename) {
    return ResponseEntity.ok(fileManager.download(filename));
  }

}
