package com.frolic.sns.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeDslRepository {

  private final JPAQueryFactory jpaQueryFactory;



}
