package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.article.dto.SingleCommentInfoDto;
import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.ArticleHashTag;
import com.modular.restfulserver.article.model.Comment;
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
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final JwtProvider jwtProvider;

  @Override
  public SingleArticleInfoDto getPostById(Long id) {
    Article article = articleRepository.findById(id)
      .orElseThrow(NotFoundResourceException::new);

    List<String> hashtags = articleHashtagRepository.findAllByArticle(article);
    UserInfoForClientDto userInfo = getUserInfoForClientDto(article.getUser());

    return getSingleArticleDto(article, hashtags, userInfo);
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
    Article newArticle = createArticle(dto, user);

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

    UserInfoForClientDto userInfo = getUserInfoForClientDto(user);

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

    Page<Article> articlePage = articleRepository.findAllByUserOrderByCreatedDate(
      user,
      pageable
    );
    return articlePage.stream()
      .map(article -> {
        List<String> hashtags = articleHashtagRepository.findAllByArticle(article);
        UserInfoForClientDto userInfo = getUserInfoForClientDto(article.getUser());
        return getSingleArticleDto(article, hashtags, userInfo);
      })
      .collect(Collectors.toList());
  }

  @Override
  public List<SingleArticleInfoDto> getEntirePostByPagination(Pageable pageable) {
    Page<Article> articlePage = articleRepository.findAll(pageable);
    return articlePage.stream()
      .map(article -> {
        List<String> hashtags = articleHashtagRepository.findAllByArticle(article);
        UserInfoForClientDto articleOwner = getUserInfoForClientDto(article.getUser());
        return getSingleArticleDto(article,hashtags, articleOwner);
      })
      .collect(Collectors.toList());
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

  private User getUserIsTokenAble(String token) {
    return userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    ).orElseThrow(UserNotFoundException::new);
  }

  private SingleArticleInfoDto getSingleArticleDto(Article article, List<String> hashtags, UserInfoForClientDto userInfo) {
    long likeCount = likeRepository.countAllByArticle(article);
    List<SingleCommentInfoDto> comments = commentRepository.findAllByArticle(article)
      .stream()
      .map(comment -> getSingleCommentDtoByEntity(comment, article, userInfo))
      .collect(Collectors.toList());

    return SingleArticleInfoDto.builder()
      .addPostId(article.getId())
      .addHashtags(hashtags)
      .addComments(comments)
      .addUserInfo(userInfo)
      .addLikeCount(likeCount)
      .addTextContent(article.getTextContent())
      .addTitle(article.getTitle())
      .build();
  }

  private SingleCommentInfoDto getSingleCommentDtoByEntity(Comment comment, Article article, UserInfoForClientDto userInfo) {
    return SingleCommentInfoDto.builder()
      .addCommentId(comment.getId())
      .addReplyUserId(comment.getReplyUserPkId())
      .addArticleId(article.getId())
      .addUserInfo(userInfo)
      .addTextContent(comment.getTextContent())
      .build();
  }

  private UserInfoForClientDto getUserInfoForClientDto(User user) {
    return UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .build();
  }

  private Article createArticle(CreatePostRequestDto dto, User user) {
    return Article.builder()
      .addTitle(dto.getTitle())
      .addTextContent(dto.getTextContent())
      .addUser(user)
      .build();
  }

}
