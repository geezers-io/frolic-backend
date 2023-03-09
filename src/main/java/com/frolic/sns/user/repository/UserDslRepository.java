package com.frolic.sns.user.repository;

import com.frolic.sns.user.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.frolic.sns.user.model.QUser.*;

@Repository
@RequiredArgsConstructor
public class UserDslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public Optional<User> findUserByEmail(String email) {
    return Optional.ofNullable(
      jpaQueryFactory
        .selectFrom(user)
        .where(user.email.eq(email))
        .fetchOne()
    );
  }

  public Optional<User> findByUsername(String username) {
    return Optional.ofNullable(
      jpaQueryFactory
        .selectFrom(user)
        .where(user.username.eq(username))
        .fetchOne()
    );
  }

  public boolean isExistsByEmail(String email) {
    return jpaQueryFactory
      .selectOne()
      .from(user)
      .where(user.email.eq(email))
      .fetchFirst() > 0;
  }

  public boolean isExistsUsername(String username) {
    return jpaQueryFactory
      .selectOne()
      .from(user)
      .where(user.username.eq(username))
      .fetchFirst() > 0;
  }

  public Optional<String> findUserRefreshToken(String email) {
    return Optional.ofNullable(
      jpaQueryFactory
        .select(user.refreshToken)
        .from(user)
        .where(user.email.eq(email))
        .fetchOne()
    );
  }

  public Optional<String> findUserEmailByPhone(String phone) {
    return Optional.ofNullable(
      jpaQueryFactory
        .select(user.email)
        .from(user)
        .where(user.phoneNumber.eq(phone))
        .fetchOne()
    );
  }

}
