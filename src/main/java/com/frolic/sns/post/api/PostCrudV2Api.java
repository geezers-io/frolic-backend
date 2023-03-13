package com.frolic.sns.post.api;

import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.post.application.v2.PostCrudManagerV2;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.dto.v2.GetPostCursorRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.dto.v2.UpdatePostRequest;
import com.frolic.sns.post.swagger.CreatePostDocs;
import com.frolic.sns.post.swagger.DeletePostDocs;
import com.frolic.sns.post.swagger.UpdatePostDocs;
import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.frolic.sns.global.common.ResponseHelper.createDataMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostCrudV2Api {

  private final UserManager userManager;

  private final PostCrudManagerV2 postCrudManager;

  @PostMapping("/list")
  public ResponseEntity<Map<String, List<PostInfo>>> getPostsApi(
    HttpServletRequest request,
    @RequestBody GetPostCursorRequest getPostCursorRequest
  ) {
    User user = userManager.getUserByHttpRequest(request);
    List<PostInfo> PostInfos = postCrudManager.getPosts(getPostCursorRequest, user);
    return ResponseEntity.ok(ResponseHelper.createDataMap(PostInfos));
  }

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
