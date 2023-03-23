package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Like;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.user.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.frolic.sns.post.model.QLike.*;

@Repository
@RequiredArgsConstructor
public class LikeDslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public Long countAllLike(User user) {
    return jpaQueryFactory.selectFrom(like)
      .where(like.user.eq(user))
      .fetchCount();
  }

  public Long countAllLike(Post post) {
    return jpaQueryFactory.selectFrom(like)
      .where(like.post.eq(post))
      .fetchCount();
  }

  public Optional<Like> findLike(User user, Post post) {
    return
      Optional.ofNullable(
        jpaQueryFactory.selectFrom(like)
          .where(like.post.eq(post), like.user.eq(user))
          .fetchOne()
      );
  }

  public boolean isExistsLike(User user, Post post) {
    return findLike(user, post).isPresent();
  }

}
