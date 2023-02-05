package com.frolic.sns.post.api;

import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.common.file.application.FileManageable;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.post.application.v2.PostCrudManagerV2;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static com.frolic.sns.global.common.ResponseHelper.createDataMap;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v2/posts")
public class PostCrudV2Api {

  private final JwtProvider jwtProvider;

  private final PostCrudManagerV2 postCrudManager;
  @PostMapping()
  public ResponseEntity<Map<String, PostInfo>> createPost(
    @Valid @RequestBody CreatePostRequest createPostRequest,
    HttpServletRequest request
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    PostInfo postInfo = postCrudManager.createPost(token, createPostRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(createDataMap(postInfo));
  }

}
