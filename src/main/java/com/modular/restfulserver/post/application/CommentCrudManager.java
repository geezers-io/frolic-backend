package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.dto.CreateCommentRequest;
import com.modular.restfulserver.post.dto.CommentDetail;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentCrudManager {

  CommentDetail getCommentById(Long commentId);

  List<CommentDetail> getCommentsByArticlePagination(Long articleId, Pageable pageable);

  List<CommentDetail> getCommentsByUserPagination(String username, Pageable pageable);

  CommentDetail createComment(String token, CreateCommentRequest dto);

  CommentDetail updateComment(String token, CreateCommentRequest dto, Long commentId);

  void deleteComment(String token, Long commentId);

}
