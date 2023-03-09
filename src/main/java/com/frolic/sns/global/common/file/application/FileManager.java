package com.frolic.sns.global.common.file.application;

import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.global.common.file.repository.FileRepository;
import com.frolic.sns.global.exception.NotFoundResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class FileManager {

  private final FileRepository fileRepository;

  public ApplicationFile getFileByName(String name) {
    return fileRepository.findByName(name).orElseThrow(NotFoundResourceException::new);
  }

}
