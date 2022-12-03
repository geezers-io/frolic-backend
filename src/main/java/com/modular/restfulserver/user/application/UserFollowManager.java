package com.modular.restfulserver.user.application;

import java.util.List;

public interface UserFollowManager {

  List<String> getFollowerListBySelf(String token);

  List<String> getFollowerListByUsername(String token, String username);

  List<String> getFollowingListBySelf(String token);

  List<String> getFollowingListByUsername(String token, String username);

  void addFollowToUsername(String token, String username);

  void removeFollowToUsername(String token, String username);

  boolean checkExistsFollow(String token, String username);
  boolean checkExistsFollowing(String token, String username);

}
