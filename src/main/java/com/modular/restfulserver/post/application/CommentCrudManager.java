package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.dto.CreateCommentRequest;
import com.modular.restfulserver.post.dto.CommentDetails;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentCrudManager {

  CommentDetails getCommentById(Long commentId);

  List<CommentDetails> getCommentsByArticlePagination(Long articleId, Pageable pageable);

  List<CommentDetails> getCommentsByUserPagination(String username, Pageable pageable);

  CommentDetails createComment(String token, CreateCommentRequest dto);

  CommentDetails updateComment(String token, CreateCommentRequest dto, Long commentId);

  void deleteComment(String token, Long commentId);

}
