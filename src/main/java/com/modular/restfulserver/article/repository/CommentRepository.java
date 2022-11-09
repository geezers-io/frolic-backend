package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
