package com.frolic.sns.post.application.v2;

import com.frolic.sns.post.model.Hashtag;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostHashTag;
import com.frolic.sns.post.repository.HashtagDslRepository;
import com.frolic.sns.post.repository.PostHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashTagManager {

  private final PostHashtagRepository postHashtagRepository;
  private final HashtagDslRepository hashtagDslRepository;

  protected void connectHashtagsWithPost(List<String> hashtags, Post post) {
    List<Hashtag> entities = hashtagDslRepository.createHashtagsIfNotExists(hashtags);
    if (entities == null) return;
    entities.forEach(entity -> {
      boolean isAlreadyExistsRelation = postHashtagRepository.existsByPostAndHashtag(post, entity);
      if (!isAlreadyExistsRelation) {
        PostHashTag postHashTag = PostHashTag.builder()
          .addPost(post)
          .addHashtag(entity)
          .build();
        postHashtagRepository.save(postHashTag);
      }
    });
  }

}
