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

  @Modifying
  @Query("" +
    "select u.username" +
    " from users u" +
    " inner join follows f" +
    " on f.followingId = :user" +
    "")
  String[] getAllByFollowerId(User user);

  @Query("" +
    "select u.username" +
    " from users u" +
    " inner join follows f" +
    " on f.followerId = :user" +
    "")
  String[] getAllByFollowingId(User user);

  @Modifying
  @Query("delete from follows f where f.followingId = :followingUser and f.followerId = :followerUser")
  void deleteFollowByFollowerIdAndFollowingId(
    User followerUser,
    User followingUser
  );

  boolean existsFollowByFollowerIdAndFollowingId(
    User followerUser,
    User followingUser
  );

}
