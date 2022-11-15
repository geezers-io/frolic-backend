package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.Comment;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByArticle(Article article);

  Page<Comment> findAllByArticleOrderByCreatedDate(Article article, Pageable pageable);

  Page<Comment> findAllByUserOrderByCreatedDate(User user, Pageable pageable);

}
