package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.QPostHashTag;
import com.frolic.sns.user.model.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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

  private JPAQuery<Post> createQueryTemplate(Long cursorId) {
    JPAQuery<Post> query = queryFactory.selectFrom(post);

    if (cursorId != null) {
      query.where(post.id.lt(cursorId));
    }

    query
      .orderBy(post.createdDate.desc())
      .limit(10);

    return query;
  }

  public List<Post> findPosts(Long cursorId) {
    return createQueryTemplate(cursorId).fetch();
  }

  public List<Post> findPostByUser(Long cursorId, Long userId) {
    return createQueryTemplate(cursorId)
      .where(post.user.id.eq(userId))
      .fetch();
  }

  public List<Post> findPostByHashtags(Long cursorId, List<String> hashtags) {
    return createQueryTemplate(cursorId)
      .join(postHashTag)
      .on(postHashTag.post.eq(post))
      .where(postHashTag.hashtag.name.in(hashtags))
      .fetch();
  }

}
