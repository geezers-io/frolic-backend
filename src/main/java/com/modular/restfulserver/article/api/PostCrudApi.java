package com.modular.restfulserver.article.api;

import com.modular.restfulserver.article.application.PostCrudManager;
import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostCrudApi {

  private final JwtProvider jwtProvider;
  private final PostCrudManager postCrudManager;

  @PostMapping("")
  public ResponseEntity<Map<String, Article>> createPostApi(
    HttpServletRequest request,
    @RequestBody @Valid CreatePostRequestDto createPostRequestDto
  ) {
    var responseData = new HashMap<String, Article>();
    Article post = postCrudManager.createPost(
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
    @PathVariable(name = "postId") Long postId
  ) {
    String token = getToken(request);
    postCrudManager.updatePostById(token, postId);
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

  private String getToken(HttpServletRequest request) {
    return jwtProvider.getTokenByHttpRequestHeader(request);
  }

}
