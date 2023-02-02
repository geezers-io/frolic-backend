package com.frolic.sns.global.common.file.repository;

import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<ApplicationFile, Long> {

  List<ApplicationFile> findAllByNameIn(List<String> filenames);

  List<ApplicationFile> findAllByPost(Post post);

}
