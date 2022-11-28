package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

  Long countAllByUser(User user);

  Page<Article> findAllByUserOrderByCreatedDate(User user, Pageable pageable);

  @Query(value = "" +
    "select posts from articles posts " +
    "join article_hashtags post_tags on posts = post_tags.article " +
    "where post_tags.hashtag in (" +
    "select h from hashtags h where h.name in (?1)" +
    ") order by posts.createdDate asc" +
    "")
  Page<Article> findAllByHashtagsAndPagination(List<String> searchHashtagList, Pageable pageable);

}
