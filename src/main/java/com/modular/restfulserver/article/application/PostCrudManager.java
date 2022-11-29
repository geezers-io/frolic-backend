package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.article.dto.UpdateArticleRequestDto;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostCrudManager {

  SingleArticleInfoDto getPostById(Long id);

  SingleArticleInfoDto updatePostById(String token, Long id, UpdateArticleRequestDto singleArticleInfoDto, List<CustomFile> customFiles);

  void deletePostById(String token, Long id);

  SingleArticleInfoDto createPost(String token, CreatePostRequestDto dto, List<CustomFile> files);

  List<SingleArticleInfoDto> getPostByTokenAndPagination(String token, Pageable pageable);

  List<SingleArticleInfoDto> getEntirePostByPagination(Pageable pageable);

  List<SingleArticleInfoDto> getSearchParamByPagination(List<String> searchList, Pageable pageable);

}
