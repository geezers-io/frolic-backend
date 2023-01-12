package com.frolic.sns.post.application;

import com.frolic.sns.post.dto.CreateCommentRequest;
import com.frolic.sns.post.dto.CommentInfo;
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
