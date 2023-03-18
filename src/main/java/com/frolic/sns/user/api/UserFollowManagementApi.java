package com.frolic.sns.user.api;

import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.user.application.UserFollowService;
import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.dto.FollowUserRequest;
import com.frolic.sns.user.model.User;
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

  private final UserFollowService userFollowService;
  private final UserManager userManager;


  @GetFollowerListMySelfDocs
  @GetMapping("/follower")
  public ResponseEntity<Map<String, List<FollowUserRequest>>> getFollowerListBySelfApi(
    HttpServletRequest request
  ) {
    User user = userManager.getUserByHttpRequest(request);
    List<FollowUserRequest> followList = userFollowService.getFollowerListBySelf(user);
    return ResponseEntity
      .ok(ResponseHelper.createDataMap(followList));
  }

  @GetFollowerListByUsernameDocs
  @GetMapping("/follower/{username}")
  public ResponseEntity<Map<String, List<FollowUserRequest>>> getFollowerListByUsernameApi(@PathVariable String username) {
    List<FollowUserRequest> followerList = userFollowService.getFollowerListByUsername(username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(followerList));
  }

  @GetFollowingListByUsernameDocs
  @GetMapping("/following/{username}")
  public ResponseEntity<Map<String, List<FollowUserRequest>>> getFollowingListByUsernameApi(@PathVariable String username) {
    List<FollowUserRequest> followingList = userFollowService.getFollowingListByUsername(username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(followingList));
  }


  @GetFollowingListMySelfDocs
  @GetMapping("/following")
  public ResponseEntity<Map<String, List<FollowUserRequest>>> getFollowingListBySelfApi(
    HttpServletRequest request
  ) {
    User user = userManager.getUserByHttpRequest(request);
    List<FollowUserRequest> followerList = userFollowService.getFollowingListBySelf(user);
    return ResponseEntity
      .ok(ResponseHelper.createDataMap(followerList));
  }

  @UserFollowerDocs
  @GetMapping("/follow")
  public ResponseEntity<Void> addFollowToUsernameApi(
    HttpServletRequest request,
    @RequestParam(name = "username") String username
  ) {
    User user = userManager.getUserByHttpRequest(request);
    userFollowService.addFollowToUsername(user, username);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteFollowDocs
  @DeleteMapping("/follow")
  public ResponseEntity<Void> removeFollowToUsernameApi(
    HttpServletRequest request,
    @RequestParam(name = "username") String username
  ) {
    User user = userManager.getUserByHttpRequest(request);
    userFollowService.removeFollowToUsername(user, username);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @isFollowDocs
  @GetMapping("/follow/exists")
  public ResponseEntity<Map<String, Boolean>> checkFollowExistsApi(HttpServletRequest request, @RequestParam(name="username") String username) {
    User user = userManager.getUserByHttpRequest(request);
    boolean isFollow = userFollowService.checkExistsFollow(user, username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(isFollow));
  }

  @isFollowingDocs
  @GetMapping("/following/exists")
  public ResponseEntity<Map<String, Boolean>> checkFollowingExistsApi(HttpServletRequest request, @RequestParam(name="username") String username) {
    User user = userManager.getUserByHttpRequest(request);
    boolean isFollowing = userFollowService.checkExistsFollowing(user, username);
    return ResponseEntity.ok(ResponseHelper.createDataMap(isFollowing));
  }

}
