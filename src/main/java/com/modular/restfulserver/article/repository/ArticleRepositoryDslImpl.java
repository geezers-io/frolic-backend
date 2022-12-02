package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.modular.restfulserver.article.model.QArticle.*;
import static com.modular.restfulserver.article.model.QArticleHashTag.*;
import static com.modular.restfulserver.article.model.QHashtag.*;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryDslImpl implements ArticleDslRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Article> findBySearchParamsByPagination(List<String> searchParams, Pageable pageable) {
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
