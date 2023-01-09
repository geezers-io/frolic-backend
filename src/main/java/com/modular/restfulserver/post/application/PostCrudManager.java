package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.dto.CreatePostRequest;
import com.modular.restfulserver.post.dto.PostDetail;
import com.modular.restfulserver.post.dto.UpdatePostRequest;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCrudManager {

  PostDetail getPostById(Long id, String token);

  PostDetail updatePostById(String token, Long id, UpdatePostRequest singleArticleInfoDto, List<CustomFile> customFiles);

  void deletePostById(String token, Long id);

  PostDetail createPost(String token, CreatePostRequest dto, List<CustomFile> files);

  List<PostDetail> getPostByTokenAndPagination(String token, Pageable pageable);

  List<PostDetail> getEntirePostByPagination(Pageable pageable, String token);

  List<PostDetail> getSearchParamByPagination(List<String> searchList, Pageable pageable, String token);

}
