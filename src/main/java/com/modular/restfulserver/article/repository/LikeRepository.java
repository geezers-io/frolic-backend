package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
