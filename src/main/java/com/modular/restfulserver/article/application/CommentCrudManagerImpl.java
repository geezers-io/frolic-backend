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
import com.modular.restfulserver.user.exception.NotPermissionException;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    return getSingleCommentInfoDto(comment);
  }

  @Override
  public List<SingleCommentInfoDto> getCommentsByArticlePagination(Long articleId, Pageable pageable) {
    Article article = articleRepository.findById(articleId)
      .orElseThrow(NotFoundResourceException::new);
    Page<Comment> commentPage = commentRepository.findAllByArticleOrderByCreatedDate(article, pageable);

    return commentPage.stream()
      .map(this::getSingleCommentInfoDto)
      .collect(Collectors.toList());
  }

  @Override
  public List<SingleCommentInfoDto> getCommentsByUserPagination(String username, Pageable pageable) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(UserNotFoundException::new);

    Page<Comment> commentPage = commentRepository.findAllByUserOrderByCreatedDate(user, pageable);

    return commentPage.stream()
      .map(this::getSingleCommentInfoDto)
      .collect(Collectors.toList());
  }

  @Override
  public SingleCommentInfoDto createComment(String token, CreateCommentRequestDto dto) {
    Comment newComment = getCommentByCreateRequestDto(dto);
    commentRepository.save(newComment);
    return getSingleCommentInfoDto(newComment);
  }

  @Override
  public SingleCommentInfoDto updateComment(String token, CreateCommentRequestDto dto, Long commentId) {
    User tokenUser = getUserIsTokenAble(token);
    boolean isSameUser = Objects.equals(
      tokenUser.getId(), dto.getOwnerId()
    );
    if (!isSameUser)
      throw new NotPermissionException();
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(NotFoundResourceException::new);

    comment.updateTextContent(dto.getTextContent());
    commentRepository.save(comment);
    return getSingleCommentInfoDto(comment);
  }

  @Override
  public void deleteComment(String token, Long commentId) {
    User tokenUser = getUserIsTokenAble(token);
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(NotFoundResourceException::new);

    boolean isSameUser = Objects.equals(tokenUser.getId(), comment.getUser().getId());
    if (!isSameUser)
      throw new NotPermissionException();

    commentRepository.delete(comment);
  }

  private UserInfoForClientDto getUserForClientInfo(User user) {
    return UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .build();
  }

  private User getUserIsTokenAble(String token) {
    return userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    ).orElseThrow(UserNotFoundException::new);
  }

  private SingleCommentInfoDto getSingleCommentInfoDto(Comment comment) {
    return SingleCommentInfoDto.builder()
      .addCommentId(comment.getId())
      .addUserInfo(getUserForClientInfo(comment.getUser()))
      .addArticleId(comment.getArticle().getId())
      .addReplyUserId(comment.getReplyUserPkId())
      .addTextContent(comment.getTextContent())
      .build();
  }

  private Comment getCommentByCreateRequestDto(CreateCommentRequestDto dto) {
    User user = userRepository.findById(dto.getOwnerId())
      .orElseThrow(UserNotFoundException::new);
    Article article = articleRepository.findById(dto.getPostId())
      .orElseThrow(NotFoundResourceException::new);

    return Comment.builder()
      .addTextContent(dto.getTextContent())
      .addReplyUserPkId(dto.getReplyUserId())
      .addUser(user)
      .addArticle(article)
      .build();
  }

}
