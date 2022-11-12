package com.modular.restfulserver.user.repository;

import com.modular.restfulserver.user.model.Follow;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

  Long countByFollowerId(User user);

  Long countByFollowingId(User user);

  @Query("" +
    "select u.username" +
    " from users u" +
    " left join follows f" +
    " on f.followingId = u.id " +
    "where f.followingId = ?1"+
    "")
  String[] getAllByFollowerId(User user);

  @Query("" +
    "select u.username" +
    " from users u" +
    " left join follows f" +
    " on f.followerId = u.id " +
    "where f.followerId = ?1" +
    "")
  String[] getAllByFollowingId(User user);

  @Modifying
  @Query("delete from follows f where f.followerId = ?1 and f.followingId = ?2")
  void deleteFollowByFollowerIdAndFollowingId(
    User followerUser,
    User followingUser
  );

  boolean existsFollowByFollowerIdAndFollowingId(
    User followerUser,
    User followingUser
  );

}
