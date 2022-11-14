package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.ArticleHashTag;
import com.modular.restfulserver.article.model.Hashtag;
import com.modular.restfulserver.article.repository.*;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.global.exception.NotFoundResourceException;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import com.modular.restfulserver.user.exception.NotPermissionException;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCrudManagerImpl implements PostCrudManager {

  private final ArticleRepository articleRepository;
  private final HashtagRepository hashtagRepository;
  private final ArticleHashtagRepository articleHashtagRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final JwtProvider jwtProvider;

  @Override
  public SingleArticleInfoDto getPostById(Long id) {
    Article article = articleRepository.findById(id)
      .orElseThrow(NotFoundResourceException::new);
    User user = article.getUser();
    List<String> hashtags = articleHashtagRepository.findAllByArticle(article);
    UserInfoForClientDto userInfo = UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getEmail())
      .build();
    Long likeCount = likeRepository.countAllByArticle(article);

    return SingleArticleInfoDto.builder()
      .addPostId(article.getId())
      .addTitle(article.getTitle())
      .addTextContent(article.getTextContent())
      .addUserInfo(userInfo)
      .addLikeCount(likeCount)
      .addComments(article.getComments())
      .addHashtags(hashtags)
      .build();
  }

  @Override
  public void updatePostById(String token, Long id, SingleArticleInfoDto singleArticleInfoDto) {
    Article article = verifyAndGetArticleIfUserRequestTargetHavePermission(token, id);
    article.updateTitle(singleArticleInfoDto.getTitle());
    article.updateTextContent(singleArticleInfoDto.getTextContent());
  }

  @Override
  public void deletePostById(String token, Long id) {
    verifyAndGetArticleIfUserRequestTargetHavePermission(token, id);
    articleRepository.deleteById(id);
  }

  @Override
  public SingleArticleInfoDto createPost(String token, CreatePostRequestDto dto) {
    User user = userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    ).orElseThrow(UserNotFoundException::new);
    List<String> hashtags = dto.getHashTagList();

    Article newArticle = Article.builder()
      .addTitle(dto.getTitle())
      .addTextContent(dto.getTextContent())
      .addUser(user)
      .build();
    articleRepository.save(newArticle);

    hashtags.forEach(tag -> {
      if (!hashtagRepository.existsByName(tag)) {
        hashtagRepository.save(
          Hashtag.builder()
            .addName(tag)
            .build()
        );
      }
      Hashtag tagEntity = hashtagRepository.findByName(tag)
          .orElseThrow(NotFoundResourceException::new);
      articleHashtagRepository.save(
        ArticleHashTag.builder()
          .addArticle(newArticle)
          .addHashtag(tagEntity)
          .build()
      );
    });

    UserInfoForClientDto userInfo = UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .build();

    return SingleArticleInfoDto.builder()
      .addPostId(newArticle.getId())
      .addTitle(newArticle.getTitle())
      .addTextContent(newArticle.getTextContent())
      .addComments(new ArrayList<>())
      .addHashtags(hashtags)
      .addLikeCount(0L)
      .addUserInfo(userInfo)
      .build();
  }

  @Override
  public List<SingleArticleInfoDto> getPostByTokenAndPagination(String token, Pageable pageable) {
    User user = userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    ).orElseThrow(UserNotFoundException::new);

    UserInfoForClientDto userInfo = UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .build();

    Page<Article> articlePage = articleRepository.findAllByUserOrderByCreatedDate(
      user,
      pageable
    );
    List<SingleArticleInfoDto> articles = articlePage.stream()
      .map(article -> {
        long likeCount = likeRepository.countAllByArticle(article);
        List<String> hashtags = articleHashtagRepository.findAllByArticle(article);
        return SingleArticleInfoDto.builder()
          .addPostId(article.getId())
          .addTitle(article.getTitle())
          .addTextContent(article.getTextContent())
          .addComments(article.getComments())
          .addLikeCount(likeCount)
          .addUserInfo(userInfo)
          .addHashtags(hashtags)
          .build();
      })
      .collect(Collectors.toList());

    return articles;
  }

  @Override
  public List<SingleArticleInfoDto> getEntirePostByPagination(Pageable pageable) {
    Page<Article> articlePage = articleRepository.findAll(pageable);
    List<SingleArticleInfoDto> articles = articlePage.stream()
      .map(article -> {
        long likeCount = likeRepository.countAllByArticle(article);
        List<String> hashtags = articleHashtagRepository.findAllByArticle(article);
        User articleOwner = article.getUser();
        UserInfoForClientDto userInfo = UserInfoForClientDto.builder()
          .addUserId(articleOwner.getId())
          .addEmail(articleOwner.getEmail())
          .addUsername(articleOwner.getUsername())
          .build();

        return SingleArticleInfoDto.builder()
          .addPostId(article.getId())
          .addTitle(article.getTitle())
          .addTextContent(article.getTextContent())
          .addComments(article.getComments())
          .addLikeCount(likeCount)
          .addUserInfo(userInfo)
          .addHashtags(hashtags)
          .build();
      })
      .collect(Collectors.toList());

    return articles;
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
