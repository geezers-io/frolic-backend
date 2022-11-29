package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.ArticleHashTag;
import com.modular.restfulserver.article.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleHashtagRepository extends JpaRepository<ArticleHashTag, Long> {

  @Query("select h.name from hashtags h join article_hashtags ah on ah.hashtag.id = h.id where ah.article = ?1")
  List<String> findAllByArticle(Article article);

  boolean existsByArticleAndHashtag(Article article, Hashtag hashtag);

  void deleteByArticleAndHashtag(Article article, Hashtag hashtags);

}
