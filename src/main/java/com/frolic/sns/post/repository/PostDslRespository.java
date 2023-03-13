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
public class PostDslRespository {

  private final JPAQueryFactory queryFactory;

  public List<Post> findPosts() {
    return queryFactory.selectFrom(post)
      .orderBy(post.createdDate.desc())
      .limit(10)
      .fetch();
  }

  public List<Post> findPostsByCursorId(Long cursorId) {
    return queryFactory.selectFrom(post)
      .where(post.id.lt(cursorId))
      .orderBy(post.createdDate.desc())
      .limit(10)
      .fetch();
  }

  /**
   * @announcement 이 부분 사용 예정인 지 미사용인 지 작성자분께서 검토 부탁드립니다!
   */
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
