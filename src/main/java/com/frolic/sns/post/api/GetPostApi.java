package com.frolic.sns.post.api;

import com.frolic.sns.post.application.GetPostService;
import com.frolic.sns.post.application.v2.PostCrudManagerV2;
import com.frolic.sns.post.dto.v2.GetPostCursorRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.swagger.GetPostListDocs;
import com.frolic.sns.post.swagger.GetPostsByHashtagsDocs;
import com.frolic.sns.post.swagger.GetUserPostsDocs;
import com.frolic.sns.user.application.UserManager;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.frolic.sns.global.common.ResponseHelper.createDataMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class GetPostApi {

  private final UserManager userManager;
  private final PostCrudManagerV2 postCrudManager;
  private final GetPostService getPostService;

  @GetPostListDocs
  @PostMapping("/list")
  public ResponseEntity<Map<String, List<PostInfo>>> getPostsApi(@RequestBody GetPostCursorRequest getPostCursorRequest) {
    List<PostInfo> postInfos = postCrudManager.getPosts(getPostCursorRequest);
    return ResponseEntity.ok(createDataMap(postInfos));
  }

  @GetUserPostsDocs
  @PostMapping("list/user")
  public ResponseEntity<Map<String, List<PostInfo>>> getUserPostsApi(
    HttpServletRequest request,
    @RequestBody GetPostCursorRequest getPostCursorRequest
  ) {
    User user = userManager.getUserByHttpRequest(request);
    List<PostInfo> postInfos = getPostService.getPostsOwnedByUser(user, getPostCursorRequest);
    return ResponseEntity.ok(createDataMap(postInfos));
  }

  @GetPostsByHashtagsDocs
  @PostMapping("/list/search")
  public ResponseEntity<Map<String, List<PostInfo>>> getPostsByHashtagsApi(
    @RequestParam List<String> hashtags,
    @RequestBody GetPostCursorRequest getPostCursorRequest
  ) {
    List<PostInfo> postInfos = getPostService.getPostsByHashtags(hashtags, getPostCursorRequest);
    return ResponseEntity.ok(createDataMap(postInfos));
  }

}
