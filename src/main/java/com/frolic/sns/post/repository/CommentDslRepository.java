package com.frolic.sns.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.frolic.sns.post.model.QComment.*;

@Repository
@RequiredArgsConstructor
public class CommentDslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public Long getCommentCount(Long postId) {
    return jpaQueryFactory.selectFrom(comment)
      .where(comment.post.id.eq(postId))
      .fetchCount();
  }

}
