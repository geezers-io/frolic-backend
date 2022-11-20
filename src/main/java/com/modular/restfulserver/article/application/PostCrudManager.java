package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCrudManager {

  SingleArticleInfoDto getPostById(Long id);

  void updatePostById(String token, Long id, SingleArticleInfoDto singleArticleInfoDto);

  void deletePostById(String token, Long id);

  SingleArticleInfoDto createPost(String token, CreatePostRequestDto dto);

  List<SingleArticleInfoDto> getPostByTokenAndPagination(String token, Pageable pageable);

  List<SingleArticleInfoDto> getEntirePostByPagination(Pageable pageable);

  List<SingleArticleInfoDto> getSearchParamByPagination(String[] searchs, Pageable pageable);

}
