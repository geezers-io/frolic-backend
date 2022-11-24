package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {}
