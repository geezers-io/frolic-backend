package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.Like;
import com.frolic.sns.user.model.User;
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
