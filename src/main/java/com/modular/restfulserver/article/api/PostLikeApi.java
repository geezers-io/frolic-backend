package com.modular.restfulserver.article.api;

import com.modular.restfulserver.article.application.PostLikeManager;
import com.modular.restfulserver.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostLikeApi {

  private final JwtProvider jwtProvider;
  private final PostLikeManager postLikeManager;


  @GetMapping("/like")
  public ResponseEntity<Map<String, Long>> likeApi(
    HttpServletRequest request,
    @RequestParam(name = "postId") Long postId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    Long count = this.postLikeManager.likePostByTokenUser(token, postId);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(wrappingMap(count));
  }

  @DeleteMapping("/like")
  public ResponseEntity<Map<String, Long>> unLikeApi(
    HttpServletRequest request,
    @RequestParam(name = "postId") Long postId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    Long count =  this.postLikeManager.unLikePostByTokenUser(token, postId);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(wrappingMap(count));
  }

  private Map<String, Long> wrappingMap(Long likeCount) {
    var map = new HashMap<String, Long>();
    map.put("data", likeCount);
    return map;
  }

}
