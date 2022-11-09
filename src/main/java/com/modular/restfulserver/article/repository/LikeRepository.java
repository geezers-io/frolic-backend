package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Like;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

  Long countAllByUser(User user);

}
