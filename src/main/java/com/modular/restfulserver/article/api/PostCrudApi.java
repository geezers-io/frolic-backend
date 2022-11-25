package com.modular.restfulserver.article.api;

import com.modular.restfulserver.article.application.PostCrudManager;
import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.global.common.ResponseHelper;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import com.modular.restfulserver.global.common.file.application.FileManager;
import com.modular.restfulserver.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostCrudApi {

  private final JwtProvider jwtProvider;
  private final PostCrudManager postCrudManager;

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Map<String, SingleArticleInfoDto>> createPostApi(
    HttpServletRequest request,
    @RequestPart @Valid CreatePostRequestDto createPostRequest,
    @RequestPart List<MultipartFile> files
  ) {
    List<CustomFile> customFiles = FileManager.createCustomFileList(files);
    SingleArticleInfoDto post = postCrudManager.createPost(getToken(request), createPostRequest, customFiles);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseHelper.createDataMap(post));
  }

  @GetMapping("/{postId}")
  public ResponseEntity<Map<String, SingleArticleInfoDto>> getPostByIdApi(
    @PathVariable(name = "postId") Long postId
  ) {
    SingleArticleInfoDto articleInfo = postCrudManager.getPostById(postId);
    return ResponseEntity.ok(ResponseHelper.createDataMap(articleInfo));
  }

  @PutMapping("/{postId}")
  public ResponseEntity<Void> updatePostByIdApi(
    HttpServletRequest request,
    @PathVariable(name = "postId") Long postId,
    @RequestBody @Valid SingleArticleInfoDto dto
  ) {
    String token = getToken(request);
    postCrudManager.updatePostById(token, postId, dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePostByIdApi(
    HttpServletRequest request,
    @PathVariable(name = "postId") Long postId
  ) {
    String token = getToken(request);
    postCrudManager.deletePostById(token, postId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/list/token")
  public ResponseEntity<Map<String, List<SingleArticleInfoDto>>> getEntirePostByTokenPaginationApi(
    HttpServletRequest request,
    Pageable pageable
  ) {
    String token = getToken(request);

    if (token == null) {
      List<SingleArticleInfoDto> data = postCrudManager.getEntirePostByPagination(pageable);
      return ResponseEntity.ok(ResponseHelper.createDataMap(data));
    }

    List<SingleArticleInfoDto> data = postCrudManager.getPostByTokenAndPagination(token, pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  @GetMapping("/list")
  public ResponseEntity<Map<String, List<SingleArticleInfoDto>>> getEntirePostByTokenPaginationApi(
    Pageable pageable
  ) {
    List<SingleArticleInfoDto> data = postCrudManager.getEntirePostByPagination(pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  @GetMapping("/search")
  public ResponseEntity<Map<String, List<SingleArticleInfoDto>>> getPostBySearchParamPaginationApi(
    Pageable pageable,
    @RequestParam Map<String, String> reqParam
  ) {
    String[] params = (String[]) reqParam.values().toArray();
    List<SingleArticleInfoDto> data = postCrudManager.getSearchParamByPagination(params, pageable);
    return ResponseEntity.ok(ResponseHelper.createDataMap(data));
  }

  private String getToken(HttpServletRequest request) {
    return jwtProvider.getTokenByHttpRequestHeader(request);
  }

}
