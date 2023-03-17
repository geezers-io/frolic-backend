package com.frolic.sns.post.api;

import com.frolic.sns.post.application.PostCrudManager;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.dto.v2.UpdatePostRequest;
import com.frolic.sns.post.swagger.CreatePostDocs;
import com.frolic.sns.post.swagger.DeletePostDocs;
import com.frolic.sns.post.swagger.UpdatePostDocs;
import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static com.frolic.sns.global.common.ResponseHelper.createDataMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostCrudV2Api {

  private final UserManager userManager;
  private final PostCrudManager postCrudManager;

  @CreatePostDocs
  @PostMapping
  public ResponseEntity<Map<String, PostInfo>> createPostApi(
    @Valid @RequestBody CreatePostRequest createPostRequest,
    HttpServletRequest request
  ) {
    User user = userManager.getUserByHttpRequest(request);
    PostInfo postInfo = postCrudManager.createPost(user, createPostRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(createDataMap(postInfo));
  }

  @UpdatePostDocs
  @PutMapping("/{postId}")
  public ResponseEntity<Map<String, PostInfo>> updatePostApi(
    HttpServletRequest request,
    @PathVariable(name = "postId") Long postId,
    @Valid @RequestBody UpdatePostRequest updatePostRequest
  ) {
    User user = userManager.getUserByHttpRequest(request);
    PostInfo updatedPostInfo = postCrudManager.updatePost(postId, user, updatePostRequest);
    return ResponseEntity.ok(createDataMap(updatedPostInfo));
  }

  @DeletePostDocs
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePostApi(HttpServletRequest request, @PathVariable(name = "postId") Long postId) {
    User user = userManager.getUserByHttpRequest(request);
    postCrudManager.deletePost(postId, user.getId());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
