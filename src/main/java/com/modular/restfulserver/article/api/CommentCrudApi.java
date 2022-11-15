package com.modular.restfulserver.article.api;

import com.modular.restfulserver.article.application.CommentCrudManager;
import com.modular.restfulserver.article.dto.SingleCommentInfoDto;
import com.modular.restfulserver.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentCrudApi {

  private final JwtProvider jwtProvider;
  private final CommentCrudManager commentCrudManager;

  @GetMapping("/{commentId}")
  public ResponseEntity<Map<String, SingleCommentInfoDto>> getCommentByIdApi(
    @PathVariable(name = "commentId") Long commentId
  ) {
    SingleCommentInfoDto info = commentCrudManager.getCommentById(commentId);
    return ResponseEntity.ok(wrapSingleComment(info));
  }

  @GetMapping("")
  public ResponseEntity<Map<String, List<SingleCommentInfoDto>>>
  getCommentsByArticleIdPaginationApi(
    @RequestParam(name = "postId") Long postId,
    @RequestParam(name = "username") String username,
    Pageable pageable
  ) {
    if (username != null) {
      List<SingleCommentInfoDto> comments = commentCrudManager
        .getCommentsByUserPagination(username, pageable);
      return ResponseEntity.ok(wrapCommentList(comments));
    }

    List<SingleCommentInfoDto> comments = commentCrudManager
      .getCommentsByArticlePagination(postId, pageable);
    return ResponseEntity.ok(wrapCommentList(comments));
  }

  private Map<String, SingleCommentInfoDto> wrapSingleComment(SingleCommentInfoDto target) {
    Map<String, SingleCommentInfoDto> map = new HashMap<>();
    map.put("data", target);
    return map;
  }

  private Map<String, List<SingleCommentInfoDto>> wrapCommentList(List<SingleCommentInfoDto> target) {
    Map<String, List<SingleCommentInfoDto>> map = new HashMap<>();
    map.put("data", target);
    return map;
  }

}
