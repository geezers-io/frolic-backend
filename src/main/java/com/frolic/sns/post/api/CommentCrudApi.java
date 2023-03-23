package com.frolic.sns.post.api;

import com.frolic.sns.post.application.CommentCrudService;
import com.frolic.sns.post.dto.CreateCommentRequest;
import com.frolic.sns.post.dto.CommentInfo;
import com.frolic.sns.post.swagger.*;
import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.auth.application.security.JwtProvider;
import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentCrudApi {

  private final JwtProvider jwtProvider;

  private final CommentCrudService commentCrudService;

  private final UserManager userManager;

  @CreateCommentDocs
  @PostMapping
  public ResponseEntity<Map<String, CommentInfo>> createCommentApi(
    HttpServletRequest request,
    @RequestBody @Valid CreateCommentRequest createCommentRequest
    ) {
    User user = userManager.getUserByHttpRequest(request);
    CommentInfo commentInfo = commentCrudService.createComment(user, createCommentRequest);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseHelper.createDataMap(commentInfo));
  }

  @DeleteCommentDocs
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteCommentApi(
    HttpServletRequest request,
    @PathVariable(name = "commentId") Long commentId
  ) {
    User user = userManager.getUserByHttpRequest(request);
    commentCrudService.deleteComment(user, commentId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @UpdateCommentDocs
  @PutMapping("/{commentId}")
  public ResponseEntity<Map<String, CommentInfo>> updateCommentApi(
    HttpServletRequest request,
    @RequestBody @Valid CreateCommentRequest createCommentRequest,
    @PathVariable(name = "commentId") Long commentId
  ) {
    User user = userManager.getUserByHttpRequest(request);
    CommentInfo commentInfo = commentCrudService.updateComment(user, createCommentRequest, commentId);
    return ResponseEntity.ok(ResponseHelper.createDataMap(commentInfo));
  }

  @GetCommentDocs
  @GetMapping("/{commentId}")
  public ResponseEntity<Map<String, CommentInfo>> getCommentByIdApi(
    @PathVariable(name = "commentId") Long commentId
  ) {
    CommentInfo info = commentCrudService.getCommentById(commentId);
    return ResponseEntity.ok(ResponseHelper.createDataMap(info));
  }

  @GetCommentListDocs
  @GetMapping("/posts/{postId}")
  public ResponseEntity<Map<String, List<CommentInfo>>> getCommentByArticleIdPaginationApi(
    @PathVariable(name = "postId") Long postId,
    Pageable pageable
  ) {
    List<CommentInfo> comments = commentCrudService.getCommentsByArticlePagination(postId, pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(comments));
  }

  @GetCommentListByUsernameDocs
  @GetMapping("/username/{username}")
  public ResponseEntity<Map<String, List<CommentInfo>>> getCommentsByUsernamePaginationApi(
    @PathVariable String username,
    Pageable pageable
    ) {
      List<CommentInfo> comments = commentCrudService.getCommentsByUserPagination(username, pageable);
      return ResponseEntity.ok(ResponseHelper.createDataMap(comments));
  }

}
