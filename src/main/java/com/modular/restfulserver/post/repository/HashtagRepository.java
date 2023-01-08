package com.modular.restfulserver.post.repository;

import com.modular.restfulserver.post.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

  boolean existsByName(String name);

  List<Hashtag> findAllByNameIn(List<String> name);

  Optional<Hashtag> findByName(String tagName);

}
