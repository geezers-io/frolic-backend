package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.ArticleHashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleHashtagRepository extends JpaRepository<ArticleHashTag, Long> {

  @Query("select h.name from hashtags h join article_hashtags ah on ah.hashtag = h.id where ah.article = ?1")
  List<String> findAllByArticle(Article article);

}
