package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.global.common.file.application.CustomFile;

import java.util.List;

public interface ArticleFileManager {

  List<String> saveFilesWithArticle(Article article, List<CustomFile> files);

  List<String> getFileDownloadUrlsByArticle(Article article);

  void deleteAllFilesByArticle(Article article);

}
