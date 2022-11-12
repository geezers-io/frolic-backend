package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByArticle(Article article);

}
