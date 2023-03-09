package com.frolic.sns.post.api;

import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.post.application.v2.PostCrudManagerV2;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.dto.v2.UpdatePostRequest;
import com.frolic.sns.post.swagger.CreatePostDocs;
import com.frolic.sns.post.swagger.DeletePostDocs;
import com.frolic.sns.post.swagger.UpdatePostDocs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @CreatePostDocs
  @PostMapping()
  public ResponseEntity<Map<String, PostInfo>> createPostApi(
    @Valid @RequestBody CreatePostRequest createPostRequest,
    HttpServletRequest request
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    PostInfo postInfo = postCrudManager.createPost(token, createPostRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(createDataMap(postInfo));
  }

  @UpdatePostDocs
  @PutMapping("/{postId}")
  public ResponseEntity<Map<String, PostInfo>> updatePostApi(
    HttpServletRequest request,
    @PathVariable(name = "postId") Long postId,
    @Valid @RequestBody UpdatePostRequest updatePostRequest
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    PostInfo updatedPostInfo = postCrudManager.updatePost(postId, token, updatePostRequest);
    return ResponseEntity.ok(createDataMap(updatedPostInfo));
  }

  @DeletePostDocs
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePostApi(HttpServletRequest request, @PathVariable(name = "postId") Long postId) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    postCrudManager.deletePost(postId, token);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
