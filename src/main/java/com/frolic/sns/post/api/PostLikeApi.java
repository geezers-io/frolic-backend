package com.frolic.sns.post.api;

import com.frolic.sns.post.application.PostLikeManager;
import com.frolic.sns.post.swagger.PostDisLikeDocs;
import com.frolic.sns.post.swagger.PostLikeDocs;
import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostLikeApi {

  private final JwtProvider jwtProvider;
  private final PostLikeManager postLikeManager;


  @PostLikeDocs
  @GetMapping("/like")
  public ResponseEntity<Map<String, Long>> likeApi(
    HttpServletRequest request,
    @RequestParam(name = "postId") Long postId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    Long count = this.postLikeManager.likePostByTokenUser(token, postId);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseHelper.createDataMap(count));
  }

  @PostDisLikeDocs
  @DeleteMapping("/like")
  public ResponseEntity<Map<String, Long>> unLikeApi(
    HttpServletRequest request,
    @RequestParam(name = "postId") Long postId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    Long count =  this.postLikeManager.unLikePostByTokenUser(token, postId);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseHelper.createDataMap(count));
  }

}
