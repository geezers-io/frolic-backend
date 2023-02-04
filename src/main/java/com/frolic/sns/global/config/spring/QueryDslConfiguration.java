package com.frolic.sns.global.config.spring;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfiguration {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

//  @Bean
//  public SQLQueryFactory sqlQueryFactory() {
//    SQLTemplates templates = new MySQLTemplates();
//    com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
//    return new SQLQueryFactory(configuration);
//  }

}
