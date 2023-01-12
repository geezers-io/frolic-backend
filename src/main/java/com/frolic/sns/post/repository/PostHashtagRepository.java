package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostHashTag;
import com.frolic.sns.post.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostHashtagRepository extends JpaRepository<PostHashTag, Long> {

  @Query("select h.name from hashtags h join post_hashtags ah on ah.hashtag.id = h.id where ah.post = ?1")
  List<String> findAllByPost(Post post);

  boolean existsByPostAndHashtag(Post post, Hashtag hashtag);

  void deleteByPostAndHashtag(Post post, Hashtag hashtags);

}
