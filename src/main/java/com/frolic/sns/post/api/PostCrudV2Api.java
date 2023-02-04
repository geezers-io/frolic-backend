package com.frolic.sns.post.api;

import com.frolic.sns.global.common.file.application.FileManageable;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostCrudV2Api {

  private final JwtProvider jwtProvider;

  private final FileManageable fileManager;

  @PostMapping()
  public ResponseEntity uploadSingleImageApi(@Valid @RequestBody CreatePostRequest createPostRequest) {
//    FileInfo fileInfo = fileManager.singleUpload(file);
    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

}
