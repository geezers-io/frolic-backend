package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.model.Post;
import com.modular.restfulserver.global.common.file.application.CustomFile;

import java.util.List;

public interface PostFileManager {

  List<String> saveFilesWithArticle(Post post, List<CustomFile> files);

  List<String> getFileDownloadUrlsByArticle(Post post);

  void deleteAllFilesByArticle(Post post);

}
