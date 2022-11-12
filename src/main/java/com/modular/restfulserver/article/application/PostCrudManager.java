package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.model.Article;

public interface PostCrudManager {

  Article getPostById(Long id);

  Article updatePostById(Long id);

  Article deletePostById(Long id);

  Article createPost();

  Article getPostByTokenAndPagination(String token, Integer offset);

  Article getEntirePostByPagination(String token, Integer offset);

}
