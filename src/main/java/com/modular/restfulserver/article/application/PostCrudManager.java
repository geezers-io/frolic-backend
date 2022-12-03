package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.article.dto.UpdateArticleRequestDto;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostCrudManager {

  SingleArticleInfoDto getPostById(Long id, String token);

  SingleArticleInfoDto updatePostById(String token, Long id, UpdateArticleRequestDto singleArticleInfoDto, List<CustomFile> customFiles);

  void deletePostById(String token, Long id);

  SingleArticleInfoDto createPost(String token, CreatePostRequestDto dto, List<CustomFile> files);

  List<SingleArticleInfoDto> getPostByTokenAndPagination(String token, Pageable pageable);

  List<SingleArticleInfoDto> getEntirePostByPagination(Pageable pageable, String token);

  List<SingleArticleInfoDto> getSearchParamByPagination(List<String> searchList, Pageable pageable, String token);

}
