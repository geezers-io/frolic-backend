package com.modular.restfulserver.post.repository;

import com.modular.restfulserver.post.model.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.modular.restfulserver.post.model.QArticle.*;
import static com.modular.restfulserver.post.model.QArticleHashTag.*;
import static com.modular.restfulserver.post.model.QHashtag.*;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryDslImpl implements PostDslRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Post> findBySearchParamsByPagination(List<String> searchParams, Pageable pageable) {
    return queryFactory.selectFrom(article)
      .join(articleHashTag)
      .on(articleHashTag.id.eq(article.id))
      .where(articleHashTag.id.in(
        queryFactory.select(hashtag.id)
          .from(hashtag)
          .where(hashtag.name.in(searchParams))
      ))
      .offset(pageable.getPageNumber())
      .limit(pageable.getPageNumber())
      .fetch();
  }

}
