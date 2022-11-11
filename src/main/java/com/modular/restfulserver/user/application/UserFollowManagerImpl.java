package com.modular.restfulserver.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFollowManagerImpl implements UserFollowManager {

  @Override
  public String[] getFollowerListBySelf(String token) {
    return new String[0];
  }

  @Override
  public String[] getFollowerListByUsername(String token, String username) {
    return new String[0];
  }

  @Override
  public String[] getFollowingListBySelf(String token) {
    return new String[0];
  }

  @Override
  public String[] getFollowingListByUsername(String token, String username) {
    return new String[0];
  }

  @Override
  public void addFollowToUsername(String token, String username) {

  }

  @Override
  public void removeFollowToUsername(String token, String username) {

  }

}
