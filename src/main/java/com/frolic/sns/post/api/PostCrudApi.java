package com.frolic.sns.post.api;

import com.frolic.sns.post.application.PostCrudManager;
import com.frolic.sns.post.dto.PostInfo;
import com.frolic.sns.post.dto.UpdatePostRequest;
import com.frolic.sns.post.swagger.*;
import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.common.file.application.CustomFile;
import com.frolic.sns.global.common.file.application.FileManager;
import com.frolic.sns.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostCrudApi {

  private final JwtProvider jwtProvider;
  private final PostCrudManager postCrudManager;

  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Map<String, PostInfo>> createPostApi(
    HttpServletRequest request,
    @RequestPart @Valid com.frolic.sns.post.dto.CreatePostRequest createPostRequest,
    @RequestPart(required = false) List<MultipartFile> files
  ) {
    List<CustomFile> customFiles = FileManager.createCustomFileList(files);
    PostInfo post = postCrudManager.createPost(getToken(request), createPostRequest, customFiles);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseHelper.createDataMap(post));
  }

  @GetPostDocs
  @GetMapping("/{postId}")
  public ResponseEntity<Map<String, PostInfo>> getPostByIdApi(
    HttpServletRequest request,
    @PathVariable(name = "postId") Long postId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    PostInfo articleInfo = postCrudManager.getPostById(postId, token);
    return ResponseEntity.ok(ResponseHelper.createDataMap(articleInfo));
  }

  @PutMapping("/{postId}")
  public ResponseEntity<Map<String, PostInfo>> updatePostByIdApi(
    HttpServletRequest request,
    @PathVariable(name = "postId") Long postId,
    @RequestPart @Valid UpdatePostRequest updateRequest,
    @RequestPart(required = false) List<MultipartFile> files
  ) {
    String token = getToken(request);
    List<CustomFile> customFiles = FileManager.createCustomFileList(files);
    PostInfo articleInfoDto = postCrudManager.updatePostById(token, postId, updateRequest, customFiles);
    return ResponseEntity.ok(ResponseHelper.createDataMap(articleInfoDto));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePostByIdApi(HttpServletRequest request, @PathVariable(name = "postId") Long postId) {
    String token = getToken(request);
    postCrudManager.deletePostById(token, postId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetPostListMySelfDocs
  @GetMapping("/list/token")
  public ResponseEntity<Map<String, List<PostInfo>>> getEntirePostByTokenPaginationApi(
    HttpServletRequest request,
    Pageable pageable
  ) {
    String token = getToken(request);

    List<PostInfo> data = postCrudManager.getPostByTokenAndPagination(token, pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  @GetMainPostListDocs
  @GetMapping("/list")
  public ResponseEntity<Map<String, List<PostInfo>>> getEntirePostByPaginationApi(
    HttpServletRequest request,
    Pageable pageable
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    List<PostInfo> data = postCrudManager.getEntirePostByPagination(pageable, token);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  @SearchPostDocs
  @GetMapping("/search")
  public ResponseEntity<Map<String, List<PostInfo>>> getPostBySearchParamPaginationApi(
    HttpServletRequest request,
    Pageable pageable,
    @RequestParam Map<String, String> reqParam
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    List<String> params = new ArrayList<>(reqParam.values());
    List<PostInfo> data = postCrudManager.getSearchParamByPagination(params, pageable, token);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  private String getToken(HttpServletRequest request) {
    return jwtProvider.getTokenByHttpRequestHeader(request);
  }

}
