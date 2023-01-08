package com.modular.restfulserver.post.repository;

import com.modular.restfulserver.post.model.Post;
import com.modular.restfulserver.post.model.Like;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

  Long countAllByUser(User user);

  Long countAllByPost(Post post);

  Optional<Like> findByPostAndUser(Post post, User user);

  boolean existsByPostAndUser(Post post, User user);

}
