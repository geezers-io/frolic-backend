package com.frolic.sns.post.application;

import com.frolic.sns.post.model.Post;
import com.frolic.sns.global.common.file.application.CustomFile;

import java.util.List;

public interface PostFileManager {

  List<String> saveFilesWithArticle(Post post, List<CustomFile> files);

  List<String> getFileDownloadUrlsByArticle(Post post);

  void deleteAllFilesByArticle(Post post);

}
