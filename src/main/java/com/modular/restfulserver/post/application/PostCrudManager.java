package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.dto.CreatePostRequest;
import com.modular.restfulserver.post.dto.PostDetails;
import com.modular.restfulserver.post.dto.UpdatePostRequest;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCrudManager {

  PostDetails getPostById(Long id, String token);

  PostDetails updatePostById(String token, Long id, UpdatePostRequest singleArticleInfoDto, List<CustomFile> customFiles);

  void deletePostById(String token, Long id);

  PostDetails createPost(String token, CreatePostRequest dto, List<CustomFile> files);

  List<PostDetails> getPostByTokenAndPagination(String token, Pageable pageable);

  List<PostDetails> getEntirePostByPagination(Pageable pageable, String token);

  List<PostDetails> getSearchParamByPagination(List<String> searchList, Pageable pageable, String token);

}
