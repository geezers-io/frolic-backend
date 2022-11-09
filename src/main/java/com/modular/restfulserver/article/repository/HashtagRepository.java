package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
