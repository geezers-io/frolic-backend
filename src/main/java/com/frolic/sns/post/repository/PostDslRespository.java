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

}
