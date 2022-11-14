package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.Like;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

  Long countAllByUser(User user);

  Long countAllByArticle(Article article);

  Optional<Like> findByArticleAndUser(Article article, User user);

  boolean existsByArticleAndUser(Article article, User user);

}
