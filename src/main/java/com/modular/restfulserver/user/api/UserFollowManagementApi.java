package com.modular.restfulserver.user.api;

import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.user.application.UserFollowManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserFollowManagementApi {

  private final UserFollowManager userFollowManager;
  private final JwtProvider jwtProvider;

  @GetMapping("/follower")
  public ResponseEntity<Map<String, String[]>> getFollowerListBySelfApi(
    HttpServletRequest request
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    String[] followList = userFollowManager.getFollowerListBySelf(token);
    return ResponseEntity
      .ok(getWrappingResponseData(followList));
  }

  @GetMapping("/following")
  public ResponseEntity<Map<String, String[]>> getFollowingListBySelfApi(
    HttpServletRequest request
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    String[] followerList = userFollowManager.getFollowingListBySelf(token);
    return ResponseEntity
      .ok(getWrappingResponseData(followerList));
  }

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

  private Map<String, String[]> getWrappingResponseData(String[] list) {
    Map<String, String[]> responseData = new HashMap<>();
    responseData.put("data", list);
    return responseData;
  }

}
