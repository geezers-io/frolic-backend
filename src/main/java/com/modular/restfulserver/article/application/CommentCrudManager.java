package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreateCommentRequestDto;
import com.modular.restfulserver.article.dto.SingleCommentInfoDto;

import java.util.List;

public interface CommentCrudManager {

  SingleCommentInfoDto getCommentById(Long commentId);

  List<SingleCommentInfoDto> getCommentsByArticlePagination(Long articleId);

  List<SingleCommentInfoDto> getCommentsByUserPagination(String username);

  SingleCommentInfoDto createComment(String token, CreateCommentRequestDto dto);

  SingleCommentInfoDto updateComment(String token, CreateCommentRequestDto dto);

  void deleteComment(String token, Long commentId);

}
