package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreateCommentRequestDto;
import com.modular.restfulserver.article.dto.SingleCommentInfoDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentCrudManager {

  SingleCommentInfoDto getCommentById(Long commentId);

  List<SingleCommentInfoDto> getCommentsByArticlePagination(Long articleId, Pageable pageable);

  List<SingleCommentInfoDto> getCommentsByUserPagination(String username, Pageable pageable);

  SingleCommentInfoDto createComment(String token, CreateCommentRequestDto dto);

  SingleCommentInfoDto updateComment(String token, CreateCommentRequestDto dto);

  void deleteComment(String token, Long commentId);

}
