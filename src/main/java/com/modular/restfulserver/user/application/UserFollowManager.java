package com.modular.restfulserver.user.application;

import com.modular.restfulserver.user.dto.FollowUserRequest;

import java.util.List;

public interface UserFollowManager {

  List<FollowUserRequest> getFollowerListBySelf(String token);

  List<FollowUserRequest> getFollowerListByUsername(String username);

  List<FollowUserRequest> getFollowingListBySelf(String token);

  List<FollowUserRequest> getFollowingListByUsername(String username);

  void addFollowToUsername(String token, String username);

  void removeFollowToUsername(String token, String username);

  boolean checkExistsFollow(String token, String username);
  boolean checkExistsFollowing(String token, String username);

}
