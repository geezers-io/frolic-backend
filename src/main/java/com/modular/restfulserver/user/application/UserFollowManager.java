package com.modular.restfulserver.user.application;

import com.modular.restfulserver.user.dto.FollowUserDto;

import java.util.List;

public interface UserFollowManager {

  List<FollowUserDto> getFollowerListBySelf(String token);

  List<FollowUserDto> getFollowerListByUsername(String token, String username);

  List<FollowUserDto> getFollowingListBySelf(String token);

  List<FollowUserDto> getFollowingListByUsername(String token, String username);

  void addFollowToUsername(String token, String username);

  void removeFollowToUsername(String token, String username);

  boolean checkExistsFollow(String token, String username);
  boolean checkExistsFollowing(String token, String username);

}
