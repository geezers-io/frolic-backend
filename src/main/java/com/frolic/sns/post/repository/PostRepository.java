package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Post;
import com.frolic.sns.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

  Long countAllByUser(User user);

  Page<Post> findAllByUserOrderByCreatedDateDesc(User user, Pageable pageable);

  @Query(value = "" +
    "select posts from posts posts " +
    "join post_hashtags post_tags on posts = post_tags.post " +
    "where post_tags.hashtag in (" +
    "select h from hashtags h where h.name in (?1)" +
    ") order by posts.createdDate desc" +
    "")
  Page<Post> findAllByHashtagsAndPagination(List<String> searchHashtagList, Pageable pageable);

  @Query("select at from posts at order by at.createdDate desc")
  Page<Post> findAllCreatedDateDesc(Pageable pageable);

}
