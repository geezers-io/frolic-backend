package com.modular.restfulserver.article.api;

import com.modular.restfulserver.article.application.PostCrudManager;
import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostCrudApi {

  private final JwtProvider jwtProvider;
  private final PostCrudManager postCrudManager;

  @PostMapping("")
  public ResponseEntity<Map<String, SingleArticleInfoDto>> createPostApi(
    HttpServletRequest request,
    @RequestBody @Valid CreatePostRequestDto createPostRequestDto
  ) {
    var responseData = new HashMap<String, SingleArticleInfoDto>();
    SingleArticleInfoDto post = postCrudManager.createPost(
      getToken(request),
      createPostRequestDto
    );
    responseData.put("data", post);
    return ResponseEntity.ok(responseData); // TODO: 2022-11-12 201 status 수정 
  }

  @GetMapping("/{postId}")
  public ResponseEntity<Map<String, SingleArticleInfoDto>> getPostByIdApi(
    @PathVariable(name = "postId") Long postId
  ) {
    SingleArticleInfoDto articleInfo = postCrudManager.getPostById(postId);
    var responseData = new HashMap<String, SingleArticleInfoDto>();
    responseData.put("data", articleInfo);
    return ResponseEntity.ok(responseData);
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
    Map<String, List<SingleArticleInfoDto>> responseData = new HashMap<>();

    if (token == null) {
      List<SingleArticleInfoDto> data = postCrudManager.getEntirePostByPagination(pageable);
      responseData.put("data", data);
      return ResponseEntity.ok(responseData);
    }

    List<SingleArticleInfoDto> data = postCrudManager.getPostByTokenAndPagination(token, pageable);
    responseData.put("data", data);
    return ResponseEntity.ok(responseData);
  }

  @GetMapping("/list")
  public ResponseEntity<Map<String, List<SingleArticleInfoDto>>> getEntirePostByTokenPaginationApi(
    Pageable pageable
  ) {
    Map<String, List<SingleArticleInfoDto>> responseData = new HashMap<>();
    List<SingleArticleInfoDto> data = postCrudManager.getEntirePostByPagination(pageable);
    responseData.put("data", data);
    return ResponseEntity.ok(responseData);
  }

  private String getToken(HttpServletRequest request) {
    return jwtProvider.getTokenByHttpRequestHeader(request);
  }

}
