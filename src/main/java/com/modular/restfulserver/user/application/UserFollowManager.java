package com.modular.restfulserver.user.application;

public interface UserFollowManager {

  String[] getFollowerListBySelf(String token);

  String[] getFollowerListByUsername(String token, String username);

  String[] getFollowingListBySelf(String token);

  String[] getFollowingListByUsername(String token, String username);

  void addFollowToUsername(String token, String username);

  void removeFollowToUsername(String token, String username);

}
