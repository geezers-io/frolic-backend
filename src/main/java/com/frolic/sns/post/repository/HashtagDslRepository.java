package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Hashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.frolic.sns.post.model.QHashtag.*;

@Repository
@RequiredArgsConstructor
public class HashtagDslRepository {

  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  public boolean existsByName(String name) {
    return queryFactory.from(hashtag)
      .where(hashtag.name.eq(name))
      .select(hashtag.id)
      .fetchFirst() > 0;
  }

  public List<Hashtag> createHashtagsIfNotExists(List<String> tags) {
    List<String> exists = queryFactory
      .select(hashtag.name)
      .from(hashtag)
      .where(hashtag.name.in(tags))
      .fetch();

    List<String> notExists = tags.stream()
      .filter((tag) -> !exists.contains(tag))
      .collect(Collectors.toList());

    // TODO: Batch 처리로 변경할 수 있음
    notExists.forEach(tag -> {
      Hashtag entity = Hashtag.builder().addName(tag).build();
      entityManager.persist(entity);
    });

    return queryFactory.selectFrom(hashtag)
      .where(hashtag.name.in(tags))
      .fetch();
  }

}
