package com.frolic.sns.post.api;

import com.frolic.sns.post.application.PostLikeService;
import com.frolic.sns.post.swagger.PostDisLikeDocs;
import com.frolic.sns.post.swagger.PostLikeDocs;
import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.frolic.sns.global.common.ResponseHelper.createDataMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/like")
public class PostLikeApi {

  private final UserManager userManager;
  private final PostLikeService postLikeService;

  @PostLikeDocs
  @GetMapping
  public ResponseEntity<Map<String, Long>> likeApi(HttpServletRequest request, @RequestParam(name = "postId") Long postId) {
    User user = userManager.getUserByHttpRequest(request);
    Long count = this.postLikeService.addLike(user, postId);
    return ResponseEntity.status(HttpStatus.CREATED).body(createDataMap(count));
  }

  @PostDisLikeDocs
  @DeleteMapping
  public ResponseEntity<Map<String, Long>> unLikeApi(HttpServletRequest request, @RequestParam(name = "postId") Long postId) {
    User user = userManager.getUserByHttpRequest(request);
    Long count =  this.postLikeService.removeLike(user, postId);
    return ResponseEntity.status(HttpStatus.OK).body(createDataMap(count));
  }

}
