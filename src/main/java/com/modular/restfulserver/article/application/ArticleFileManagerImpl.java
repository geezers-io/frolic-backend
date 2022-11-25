package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.File;
import com.modular.restfulserver.article.repository.FileRepository;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import com.modular.restfulserver.global.common.file.application.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleFileManagerImpl implements ArticleFileManager {

  private final FileRepository fileRepository;
  private final FileManager fileManager;

  @Override
  public List<String> saveFilesWithArticle(Article article, List<CustomFile> files) {
    if (files.size() == 0) return new ArrayList<>();

    fileManager.multipleFileUpload(files);

    files.forEach(customFile -> {
        File newFile = File.createFileByCustomFile(customFile, article);
        fileRepository.save(newFile);
    });

    return files.stream()
      .map(CustomFile::getDownloadUrl)
      .collect(Collectors.toList());
  }

  @Override
  public List<String> getFileDownloadUrlsByArticle(Article article) {
    return article.getFiles().stream()
      .map(File::getDownloadUrl)
      .collect(Collectors.toList());
  }

  @Override
  public void deleteAllFilesByArticle(Article article) {
    fileRepository.deleteAll(article.getFiles());
  }

}
