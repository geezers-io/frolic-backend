package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreateCommentRequestDto;
import com.modular.restfulserver.article.dto.SingleCommentInfoDto;
import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.Comment;
import com.modular.restfulserver.article.repository.ArticleRepository;
import com.modular.restfulserver.article.repository.CommentRepository;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.global.exception.NotFoundResourceException;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentCrudManagerImpl implements CommentCrudManager {

  private final JwtProvider jwtProvider;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;

  @Override
  public SingleCommentInfoDto getCommentById(Long commentId) {
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(NotFoundResourceException::new);
    return SingleCommentInfoDto.builder()
      .addCommentId(commentId)
      .addTextContent(comment.getTextContent())
      .addArticleId(comment.getArticle().getId())
      .addReplyUserId(comment.getReplyUserPkId())
      .addUserInfo(getUserForClientInfo(comment.getUser()))
      .build();
  }

  @Override
  public List<SingleCommentInfoDto> getCommentsByArticlePagination(Long articleId, Pageable pageable) {
    Article article = articleRepository.findById(articleId)
      .orElseThrow(NotFoundResourceException::new);

    Page<Comment> commentPage = commentRepository.findAllByArticleOrderByCreatedDate(
      article, pageable
    );

    List<SingleCommentInfoDto> comments = commentPage.stream()
      .map(comment -> SingleCommentInfoDto.builder()
        .addArticleId(articleId)
        .addCommentId(comment.getId())
        .addReplyUserId(comment.getReplyUserPkId())
        .addTextContent(comment.getTextContent())
        .addUserInfo(getUserForClientInfo(comment.getUser()))
        .build()
      )
      .collect(Collectors.toList());

    return comments;
  }

  @Override
  public List<SingleCommentInfoDto> getCommentsByUserPagination(String username, Pageable pageable) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(UserNotFoundException::new);

    Page<Comment> commentPage = commentRepository.findAllByUserOrderByCreatedDate(
      user, pageable
    );

    List<SingleCommentInfoDto> comments = commentPage.stream()
      .map(comment -> SingleCommentInfoDto.builder()
        .addArticleId(comment.getArticle().getId())
        .addCommentId(comment.getId())
        .addReplyUserId(comment.getReplyUserPkId())
        .addTextContent(comment.getTextContent())
        .addUserInfo(getUserForClientInfo(comment.getUser()))
        .build()
      )
      .collect(Collectors.toList());

    return comments;
  }

  @Override
  public SingleCommentInfoDto createComment(String token, CreateCommentRequestDto dto) {
    return null;
  }

  @Override
  public SingleCommentInfoDto updateComment(String token, CreateCommentRequestDto dto) {
    return null;
  }

  @Override
  public void deleteComment(String token, Long commentId) {

  }

  public UserInfoForClientDto getUserForClientInfo(User user) {
    return UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .build();
  }

}
