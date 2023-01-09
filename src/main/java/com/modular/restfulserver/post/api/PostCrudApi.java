package com.modular.restfulserver.post.api;

import com.modular.restfulserver.post.application.PostCrudManager;
import com.modular.restfulserver.post.dto.CreatePostRequest;
import com.modular.restfulserver.post.dto.PostDetail;
import com.modular.restfulserver.post.dto.UpdatePostRequest;
import com.modular.restfulserver.post.swagger.*;
import com.modular.restfulserver.global.common.ResponseHelper;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import com.modular.restfulserver.global.common.file.application.FileManager;
import com.modular.restfulserver.global.config.security.JwtProvider;
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

  @CreatePostDocs
  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Map<String, PostDetail>> createPostApi(
    HttpServletRequest request,
    @RequestPart @Valid CreatePostRequest createPostRequest,
    @RequestPart(required = false) List<MultipartFile> files
  ) {
    List<CustomFile> customFiles = FileManager.createCustomFileList(files);
    PostDetail post = postCrudManager.createPost(getToken(request), createPostRequest, customFiles);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseHelper.createDataMap(post));
  }

  @GetPostDocs
  @GetMapping("/{postId}")
  public ResponseEntity<Map<String, PostDetail>> getPostByIdApi(
    HttpServletRequest request,
    @PathVariable(name = "postId") Long postId
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    PostDetail articleInfo = postCrudManager.getPostById(postId, token);
    return ResponseEntity.ok(ResponseHelper.createDataMap(articleInfo));
  }

  @UpdatePostDocs
  @PutMapping("/{postId}")
  public ResponseEntity<Map<String, PostDetail>> updatePostByIdApi(
    HttpServletRequest request,
    @PathVariable(name = "postId") Long postId,
    @RequestPart @Valid UpdatePostRequest updateRequest,
    @RequestPart(required = false) List<MultipartFile> files
  ) {
    String token = getToken(request);
    List<CustomFile> customFiles = FileManager.createCustomFileList(files);
    PostDetail articleInfoDto = postCrudManager.updatePostById(token, postId, updateRequest, customFiles);
    return ResponseEntity.ok(ResponseHelper.createDataMap(articleInfoDto));
  }

  @DeletePostDocs
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePostByIdApi(HttpServletRequest request, @PathVariable(name = "postId") Long postId) {
    String token = getToken(request);
    postCrudManager.deletePostById(token, postId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetPostListMySelfDocs
  @GetMapping("/list/token")
  public ResponseEntity<Map<String, List<PostDetail>>> getEntirePostByTokenPaginationApi(
    HttpServletRequest request,
    Pageable pageable
  ) {
    String token = getToken(request);

    List<PostDetail> data = postCrudManager.getPostByTokenAndPagination(token, pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  @GetMainPostListDocs
  @GetMapping("/list")
  public ResponseEntity<Map<String, List<PostDetail>>> getEntirePostByPaginationApi(
    HttpServletRequest request,
    Pageable pageable
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    List<PostDetail> data = postCrudManager.getEntirePostByPagination(pageable, token);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  @SearchPostDocs
  @GetMapping("/search")
  public ResponseEntity<Map<String, List<PostDetail>>> getPostBySearchParamPaginationApi(
    HttpServletRequest request,
    Pageable pageable,
    @RequestParam Map<String, String> reqParam
  ) {
    String token = jwtProvider.getTokenByHttpRequestHeader(request);
    List<String> params = new ArrayList<>(reqParam.values());
    List<PostDetail> data = postCrudManager.getSearchParamByPagination(params, pageable, token);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  private String getToken(HttpServletRequest request) {
    return jwtProvider.getTokenByHttpRequestHeader(request);
  }

}
