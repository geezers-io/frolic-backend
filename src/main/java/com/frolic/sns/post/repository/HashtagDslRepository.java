package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Hashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.frolic.sns.post.model.QHashtag.*;

@Repository
@RequiredArgsConstructor
public class HashtagDslRepository {

  private final JPAQueryFactory queryFactory;
  private final SQLQueryFactory sqlQueryFactory;

  public boolean existsByName(String name) {
    return queryFactory.from(hashtag)
      .where(hashtag.name.eq(name))
      .select(hashtag.id)
      .fetchFirst() > 0;
  }

  public List<Hashtag> createHashtagsIfNotExists(List<String> tags) {
    List<String> exists = queryFactory.from(hashtag)
      .where(hashtag.name.in(tags))
      .select(hashtag.name)
      .fetch();

    List<String> notExists = tags.stream()
      .filter((tag) -> !exists.contains(tag))
      .collect(Collectors.toList());

    // TODO: Batch 처리로 변경할 수 있음
    notExists.forEach(tag ->
      queryFactory.insert(hashtag)
        .set(hashtag.name, tag)
        .execute()
    );

    return queryFactory.select(hashtag)
      .where(hashtag.name.in(tags))
      .fetch();
//    SQLInsertClause insertClause = sqlQueryFactory.insert((RelationalPath<?>) hashtag);
//    tags.forEach(tag -> insertClause.set(hashtag.name, tag).addBatch());
//    insertClause.execute();
  }

}
