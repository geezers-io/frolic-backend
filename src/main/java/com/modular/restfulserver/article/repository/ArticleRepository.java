package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
