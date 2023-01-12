package com.frolic.sns.user.api;

import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.user.application.UserFollowManager;
import com.frolic.sns.user.dto.FollowUserRequest;
import com.frolic.sns.user.swagger.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserFollowManagementApi {

  private final UserFollowManager userFollowManager;
  private final JwtProvider jwtProvider;


  @GetFollowerListMySelfDocs
  @GetMapping("/follower")
  public ResponseEntity<Map<String, List<FollowUserRequest>>> getFollowerListBySelfApi(
    HttpServletRequest request
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    List<FollowUserRequest> followList = userFollowManager.getFollowerListBySelf(token);
    return ResponseEntity
      .ok(ResponseHelper.createDataMap(followList));
  }

  @GetFollowerListByUsernameDocs
  @GetMapping("/follower/{username}")
  public ResponseEntity<Map<String, List<FollowUserRequest>>> getFollowerListByUsernameApi(@PathVariable String username) {
    List<FollowUserRequest> followerList = userFollowManager.getFollowerListByUsername(username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(followerList));
  }

  @GetFollowingListByUsernameDocs
  @GetMapping("/following/{username}")
  public ResponseEntity<Map<String, List<FollowUserRequest>>> getFollowingListByUsernameApi(@PathVariable String username) {
    List<FollowUserRequest> followingList = userFollowManager.getFollowingListByUsername(username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(followingList));
  }


  @GetFollowingListMySelfDocs
  @GetMapping("/following")
  public ResponseEntity<Map<String, List<FollowUserRequest>>> getFollowingListBySelfApi(
    HttpServletRequest request
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    List<FollowUserRequest> followerList = userFollowManager.getFollowingListBySelf(token);
    return ResponseEntity
      .ok(ResponseHelper.createDataMap(followerList));
  }

  @UserFollowerDocs
  @GetMapping("/follow")
  public ResponseEntity<Void> addFollowToUsernameApi(
    HttpServletRequest request,
    @RequestParam(name = "username") String username
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    userFollowManager.addFollowToUsername(token, username);
    return ResponseEntity
      .status(HttpStatus.OK)
      .build();
  }

  @DeleteFollowDocs
  @DeleteMapping("/follow")
  public ResponseEntity<Void> removeFollowToUsernameApi(
    HttpServletRequest request,
    @RequestParam(name = "username") String username
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    userFollowManager.removeFollowToUsername(token, username);
    return ResponseEntity
      .status(HttpStatus.OK)
      .build();
  }

  @isFollowDocs
  @GetMapping("/follow/exists")
  public ResponseEntity<Map<String, Boolean>> checkFollowExistsApi(HttpServletRequest request, @RequestParam(name="username") String username) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    boolean isFollow = userFollowManager.checkExistsFollow(token, username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(isFollow));
  }

  @isFollowingDocs
  @GetMapping("/following/exists")
  public ResponseEntity<Map<String, Boolean>> checkFollowingExistsApi(HttpServletRequest request, @RequestParam(name="username") String username) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    boolean isFollowing = userFollowManager.checkExistsFollowing(token, username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(isFollowing));
  }

}
