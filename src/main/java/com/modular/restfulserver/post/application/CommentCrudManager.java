package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.dto.CreateCommentRequest;
import com.modular.restfulserver.post.dto.CommentInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentCrudManager {

  CommentInfo getCommentById(Long commentId);

  List<CommentInfo> getCommentsByArticlePagination(Long articleId, Pageable pageable);

  List<CommentInfo> getCommentsByUserPagination(String username, Pageable pageable);

  CommentInfo createComment(String token, CreateCommentRequest dto);

  CommentInfo updateComment(String token, CreateCommentRequest dto, Long commentId);

  void deleteComment(String token, Long commentId);

}
