package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.frolic.sns.post.model.QPost.*;
import static com.frolic.sns.post.model.QPostHashTag.*;
import static com.frolic.sns.post.model.QHashtag.*;

@Repository
@RequiredArgsConstructor
public class PostRepositoryDslImpl implements PostDslRepository {

  private final JPAQueryFactory queryFactory;
/*
  @Override
  public List<Post> findBySearchParamsByPagination(List<String> searchParams, Pageable pageable) {
    return queryFactory.selectFrom(post)
      .join(postHashTag)
      .on(postHashTag.id.eq(post.id))
      .where(postHashTag.id.in(
        queryFactory.select(hashtag.id)
          .from(hashtag)
          .where(hashtag.name.in(searchParams))
      ))
      .offset(pageable.getPageNumber())
      .limit(pageable.getPageNumber())
      .fetch();
  }
*/
  @Override
  public List<Post> findBySearchParamsByPagination(List<String> searchParams, Long cursorId, Pageable pageable) {
    return queryFactory.selectFrom(post)
            .join(postHashTag)
            .on(postHashTag.id.eq(post.id))
            .where(postHashTag.id.in(
                    queryFactory.select(hashtag.id)
                            .from(hashtag)
                            .where(hashtag.name.in(searchParams))
            ), eqCursorId(cursorId))
            .limit(pageable.getPageNumber())
            .fetch();
  }

  private BooleanExpression eqCursorId(Long cursorId) { // (7)
    if (cursorId != null) {
      return post.id.gt(cursorId);
    }
    return null;
  }
}
