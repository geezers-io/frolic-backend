package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

  List<File> findAllByNameIn(List<String> filenames);

  List<File> findAllByArticle(Article article);

}
