package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.article.model.Article;

public interface PostCrudManager {

  SingleArticleInfoDto getPostById(Long id);

  void updatePostById(String token, Long id, SingleArticleInfoDto singleArticleInfoDto);

  void deletePostById(String token, Long id);

  SingleArticleInfoDto createPost(String token, CreatePostRequestDto dto);

  Article getPostByTokenAndPagination(String token, Integer offset);

  Article getEntirePostByPagination(Integer offset);

}
