package com.modular.restfulserver.post.repository;

import com.modular.restfulserver.post.model.Post;
import com.modular.restfulserver.post.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

  List<File> findAllByNameIn(List<String> filenames);

  List<File> findAllByPost(Post post);

}
