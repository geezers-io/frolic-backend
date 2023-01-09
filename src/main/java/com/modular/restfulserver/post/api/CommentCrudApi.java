package com.modular.restfulserver.post.api;

import com.modular.restfulserver.post.application.CommentCrudManager;
import com.modular.restfulserver.post.dto.CreateCommentRequest;
import com.modular.restfulserver.post.dto.CommentDetails;
import com.modular.restfulserver.post.swagger.*;
import com.modular.restfulserver.global.common.ResponseHelper;
import com.modular.restfulserver.global.config.security.JwtProvider;
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
  public ResponseEntity<Map<String, CommentDetails>> createCommentApi(
    HttpServletRequest request,
    @RequestBody @Valid CreateCommentRequest dto
    ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    CommentDetails commentDetails = commentCrudManager.createComment(
      token,dto
    );
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseHelper.createDataMap(commentDetails));
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
  public ResponseEntity<Map<String, CommentDetails>> updateCommentApi(
    HttpServletRequest request,
    @RequestBody @Valid CreateCommentRequest dto,
    @PathVariable(name = "commentId") Long commentId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    CommentDetails commentDetails = commentCrudManager.updateComment(
      token, dto, commentId
    );
    return ResponseEntity.ok(ResponseHelper.createDataMap(commentDetails));
  }

  @GetCommentDocs
  @GetMapping("/{commentId}")
  public ResponseEntity<Map<String, CommentDetails>> getCommentByIdApi(
    @PathVariable(name = "commentId") Long commentId
  ) {
    CommentDetails info = commentCrudManager.getCommentById(commentId);
    return ResponseEntity.ok(ResponseHelper.createDataMap(info));
  }

  @GetCommentListDocs
  @GetMapping("/posts/{postId}")
  public ResponseEntity<Map<String, List<CommentDetails>>> getCommentByArticleIdPaginationApi(
    @PathVariable(name = "postId") Long postId,
    Pageable pageable
  ) {
    List<CommentDetails> comments = commentCrudManager
      .getCommentsByArticlePagination(postId, pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(comments));
  }

  @GetCommentListByUsernameDocs
  @GetMapping("/username/{username}")
  public ResponseEntity<Map<String, List<CommentDetails>>> getCommentsByUsernamePaginationApi(
    @PathVariable String username,
    Pageable pageable
    ) {
      List<CommentDetails> comments = commentCrudManager
        .getCommentsByUserPagination(username, pageable);
      return ResponseEntity.ok(ResponseHelper.createDataMap(comments));
  }

}
