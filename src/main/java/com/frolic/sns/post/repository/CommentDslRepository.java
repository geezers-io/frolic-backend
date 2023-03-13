package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

  public Optional<Comment> getComment(Long commentId) {
    return Optional.ofNullable(
      jpaQueryFactory.selectFrom(comment)
        .where(comment.id.eq(commentId))
        .fetchOne()
    );
  }

}
