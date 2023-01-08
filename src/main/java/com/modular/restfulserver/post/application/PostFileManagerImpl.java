package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.model.Post;
import com.modular.restfulserver.post.model.File;
import com.modular.restfulserver.post.repository.FileRepository;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import com.modular.restfulserver.global.common.file.application.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostFileManagerImpl implements PostFileManager {

  private final FileRepository fileRepository;
  private final FileManager fileManager;

  @Override
  public List<String> saveFilesWithArticle(Post post, List<CustomFile> files) {
    if (files.size() == 0) return new ArrayList<>();

    fileManager.multipleFileUpload(files);

    files.forEach(customFile -> {
        File newFile = File.createFileByCustomFile(customFile, post);
        fileRepository.save(newFile);
    });

    return files.stream()
      .map(CustomFile::getDownloadUrl)
      .collect(Collectors.toList());
  }

  @Override
  public List<String> getFileDownloadUrlsByArticle(Post post) {
    return post.getFiles().stream()
      .map(File::getDownloadUrl)
      .collect(Collectors.toList());
  }

  @Override
  public void deleteAllFilesByArticle(Post post) {
    fileRepository.deleteAll(post.getFiles());
  }

}
