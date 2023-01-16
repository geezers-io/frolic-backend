package com.frolic.sns.post.application;

import com.frolic.sns.post.dto.CreatePostRequest;
import com.frolic.sns.post.dto.PostInfo;
import com.frolic.sns.post.dto.UpdatePostRequest;
import com.frolic.sns.global.common.file.application.CustomFile;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCrudManager {

  PostInfo getPostById(Long id, String token);

  PostInfo updatePostById(String token, Long id, UpdatePostRequest singleArticleInfoDto, List<CustomFile> customFiles);

  void deletePostById(String token, Long id);

  PostInfo createPost(String token, CreatePostRequest dto, List<CustomFile> files);

  List<PostInfo> getPostByTokenAndPagination(String token, Pageable pageable);

  List<PostInfo> getEntirePostByPagination(Pageable pageable, String token);

  List<PostInfo> getSearchParamByPagination(List<String> searchList, Pageable pageable, String token);

}
