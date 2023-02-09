package com.frolic.sns.post.application;

import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.global.common.file.repository.FileRepository;
import com.frolic.sns.global.common.file.application.CustomFile;
import com.frolic.sns.global.common.file.application.FileManager;
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
        ApplicationFile newApplicationFile = ApplicationFile.createFileByCustomFile(customFile, post);
        fileRepository.save(newApplicationFile);
    });

    return files.stream()
      .map(CustomFile::getDownloadUrl)
      .collect(Collectors.toList());
  }

  @Override
  public List<String> getFileDownloadUrlsByArticle(Post post) {
    return post.getApplicationFiles().stream()
      .map(com.frolic.sns.global.common.file.model.ApplicationFile::getDownloadUrl)
      .collect(Collectors.toList());
  }

  @Override
  public void deleteAllFilesByArticle(Post post) {
    fileRepository.deleteAll(post.getApplicationFiles());
  }

}
