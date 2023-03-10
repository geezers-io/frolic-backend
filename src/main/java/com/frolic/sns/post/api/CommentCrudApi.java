package com.frolic.sns.post.api;

import com.frolic.sns.post.application.CommentCrudManager;
import com.frolic.sns.post.dto.CreateCommentRequest;
import com.frolic.sns.post.dto.CommentInfo;
import com.frolic.sns.post.swagger.*;
import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.config.security.JwtProvider;
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
  private final CommentCrudManager commentCrudManager;

  @CreateCommentDocs
  @PostMapping("")
  public ResponseEntity<Map<String, CommentInfo>> createCommentApi(
    HttpServletRequest request,
    @RequestBody @Valid CreateCommentRequest dto
    ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    CommentInfo commentInfo = commentCrudManager.createComment(token, dto);
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
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    commentCrudManager.deleteComment(token, commentId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @UpdateCommentDocs
  @PutMapping("/{commentId}")
  public ResponseEntity<Map<String, CommentInfo>> updateCommentApi(
    HttpServletRequest request,
    @RequestBody @Valid CreateCommentRequest dto,
    @PathVariable(name = "commentId") Long commentId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    CommentInfo commentInfo = commentCrudManager.updateComment(
      token, dto, commentId
    );
    return ResponseEntity.ok(ResponseHelper.createDataMap(commentInfo));
  }

  @GetCommentDocs
  @GetMapping("/{commentId}")
  public ResponseEntity<Map<String, CommentInfo>> getCommentByIdApi(
    @PathVariable(name = "commentId") Long commentId
  ) {
    CommentInfo info = commentCrudManager.getCommentById(commentId);
    return ResponseEntity.ok(ResponseHelper.createDataMap(info));
  }

  @GetCommentListDocs
  @GetMapping("/posts/{postId}")
  public ResponseEntity<Map<String, List<CommentInfo>>> getCommentByArticleIdPaginationApi(
    @PathVariable(name = "postId") Long postId,
    Pageable pageable
  ) {
    List<CommentInfo> comments = commentCrudManager
      .getCommentsByArticlePagination(postId, pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(comments));
  }

  @GetCommentListByUsernameDocs
  @GetMapping("/username/{username}")
  public ResponseEntity<Map<String, List<CommentInfo>>> getCommentsByUsernamePaginationApi(
    @PathVariable String username,
    Pageable pageable
    ) {
      List<CommentInfo> comments = commentCrudManager
        .getCommentsByUserPagination(username, pageable);
      return ResponseEntity.ok(ResponseHelper.createDataMap(comments));
  }

}
