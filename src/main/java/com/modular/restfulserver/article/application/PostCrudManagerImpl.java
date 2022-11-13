package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.Comment;
import com.modular.restfulserver.article.model.Hashtag;
import com.modular.restfulserver.article.repository.ArticleRepository;
import com.modular.restfulserver.article.repository.CommentRepository;
import com.modular.restfulserver.article.repository.HashtagRepository;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.global.exception.NotFoundResourceException;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import com.modular.restfulserver.user.exception.NotPermissionException;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostCrudManagerImpl implements PostCrudManager {

  private final ArticleRepository articleRepository;
  private final HashtagRepository hashtagRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final JwtProvider jwtProvider;

  @Override
  public SingleArticleInfoDto getPostById(Long id) {
    Article article = articleRepository.findById(id)
      .orElseThrow(NotFoundResourceException::new);
    User user = article.getUser();
    List<Comment> comments = commentRepository.findAllByArticle(article);
    List<String> hashtags = hashtagRepository.findAllByArticle(article);
    UserInfoForClientDto userInfo = UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getEmail())
      .build();

    return SingleArticleInfoDto.builder()
      .addPostId(article.getId())
      .addTitle(article.getTitle())
      .addTextContent(article.getTextContent())
      .addUserInfo(userInfo)
      .addComments(comments)
      .addHashtags(hashtags)
      .build();
  }

  @Override
  public void updatePostById(String token, Long id) {
    Article article = verifyAndGetArticleIfUserRequestTargetHavePermission(token, id);
    // TODO: 2022-11-13 업데이트 로직 작성 
  }

  @Override
  public void deletePostById(String token, Long id) {
    verifyAndGetArticleIfUserRequestTargetHavePermission(token, id);
    articleRepository.deleteById(id);
  }

  @Override
  public Article createPost(String token, CreatePostRequestDto dto) {
    return null;
  }

  @Override
  public Article getPostByTokenAndPagination(String token, Integer offset) {
    return null;
  }

  @Override
  public Article getEntirePostByPagination(Integer offset) {
    return null;
  }

  private Article verifyAndGetArticleIfUserRequestTargetHavePermission(String token, Long articleId) {
    User user = userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    ).orElseThrow(UserNotFoundException::new);
    Article article = articleRepository.findById(articleId)
      .orElseThrow(NotFoundResourceException::new);
    Long articleUserId = article.getUser().getId();
    if (!Objects.equals(user.getId(), articleUserId))
      throw new NotPermissionException();
    return article;
  }

}
