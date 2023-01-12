package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

  List<File> findAllByNameIn(List<String> filenames);

  List<File> findAllByPost(Post post);

}
