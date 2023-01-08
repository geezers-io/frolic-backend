package com.modular.restfulserver.post.repository;

import com.modular.restfulserver.post.model.Post;
import com.modular.restfulserver.post.model.Comment;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);

  Page<Comment> findAllByPostOrderByCreatedDate(Post post, Pageable pageable);

  Page<Comment> findAllByUserOrderByCreatedDate(User user, Pageable pageable);

}
