package com.frolic.sns.post.repository;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.post.model.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile, Long> {

}
