package com.modular.restfulserver.article.api;

import com.modular.restfulserver.article.application.CommentCrudManager;
import com.modular.restfulserver.article.dto.CreateCommentRequestDto;
import com.modular.restfulserver.article.dto.SingleCommentInfoDto;
import com.modular.restfulserver.article.swagger.*;
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
  public ResponseEntity<Map<String, SingleCommentInfoDto>> createCommentApi(
    HttpServletRequest request,
    @RequestBody @Valid CreateCommentRequestDto dto
    ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    SingleCommentInfoDto commentInfo = commentCrudManager.createComment(
      token,dto
    );
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
  public ResponseEntity<Map<String, SingleCommentInfoDto>> updateCommentApi(
    HttpServletRequest request,
    @RequestBody @Valid CreateCommentRequestDto dto,
    @PathVariable(name = "commentId") Long commentId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    SingleCommentInfoDto commentInfo = commentCrudManager.updateComment(
      token, dto, commentId
    );
    return ResponseEntity.ok(ResponseHelper.createDataMap(commentInfo));
  }

  @GetCommentDocs
  @GetMapping("/{commentId}")
  public ResponseEntity<Map<String, SingleCommentInfoDto>> getCommentByIdApi(
    @PathVariable(name = "commentId") Long commentId
  ) {
    SingleCommentInfoDto info = commentCrudManager.getCommentById(commentId);
    return ResponseEntity.ok(ResponseHelper.createDataMap(info));
  }

  @GetCommentListDocs
  @GetMapping("/posts/{postId}")
  public ResponseEntity<Map<String, List<SingleCommentInfoDto>>> getCommentByArticleIdPaginationApi(
    @PathVariable(name = "postId") Long postId,
    Pageable pageable
  ) {
    List<SingleCommentInfoDto> comments = commentCrudManager
      .getCommentsByArticlePagination(postId, pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(comments));
  }

  @GetCommentListByUsernameDocs
  @GetMapping("/username/{username}")
  public ResponseEntity<Map<String, List<SingleCommentInfoDto>>> getCommentsByUsernamePaginationApi(
    @PathVariable String username,
    Pageable pageable
    ) {
      List<SingleCommentInfoDto> comments = commentCrudManager
        .getCommentsByUserPagination(username, pageable);
      return ResponseEntity.ok(ResponseHelper.createDataMap(comments));
  }

}
