package com.frolic.sns.post.application;

import com.frolic.sns.post.application.v2.CommentManager;
import com.frolic.sns.post.dto.CreateCommentRequest;
import com.frolic.sns.post.dto.CommentInfo;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.Comment;
import com.frolic.sns.post.repository.PostRepository;
import com.frolic.sns.post.repository.CommentRepository;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.global.exception.NotFoundResourceException;
import com.frolic.sns.user.exception.NotPermissionException;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentCrudService {

  private final JwtProvider jwtProvider;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final PostRepository postRepository;

  private final CommentManager commentManager;

  public CommentInfo getCommentById(Long commentId) {
    return commentManager.getCommentInfo(commentId);
  }

  public List<CommentInfo> getCommentsByArticlePagination(Long articleId, Pageable pageable) {
    Post post = postRepository.findById(articleId).orElseThrow(NotFoundResourceException::new);
    Page<Comment> commentPage = commentRepository.findAllByPostOrderByCreatedDate(post, pageable);

    return commentPage.stream()
      .map(CommentInfo::from)
      .collect(Collectors.toList());
  }

  public List<CommentInfo> getCommentsByUserPagination(String username, Pageable pageable) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(UserNotFoundException::new);

    Page<Comment> commentPage = commentRepository.findAllByUserOrderByCreatedDate(user, pageable);

    return commentPage.stream()
      .map(CommentInfo::from)
      .collect(Collectors.toList());
  }

  public CommentInfo createComment(User user, CreateCommentRequest createCommentRequest) {
    Comment newComment = getCommentByCreateRequestDto(user, createCommentRequest);
    commentRepository.save(newComment);
    return CommentInfo.from(newComment);
  }

  public CommentInfo updateComment(User user, CreateCommentRequest updateCommentInfo, Long commentId) {
    Post post = postRepository.findById(updateCommentInfo.getPostId()).orElseThrow(NotFoundResourceException::new);
    boolean isSameUser = Objects.equals(user.getId(),  post.getUser().getId());
    if (!isSameUser)
      throw new NotPermissionException();
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(NotFoundResourceException::new);

    comment.updateTextContent(updateCommentInfo.getTextContent());
    commentRepository.save(comment);
    return CommentInfo.from(comment);
  }

  public void deleteComment(String token, Long commentId) {
    User tokenUser = getUserIsTokenAble(token);
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(NotFoundResourceException::new);

    boolean isSameUser = Objects.equals(tokenUser.getId(), comment.getUser().getId());
    if (!isSameUser)
      throw new NotPermissionException();

    commentRepository.delete(comment);
  }

  private User getUserIsTokenAble(String token) {
    return userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    ).orElseThrow(UserNotFoundException::new);
  }

  private Comment getCommentByCreateRequestDto(User user, CreateCommentRequest createCommentRequest) {
    Post post = postRepository.findById(createCommentRequest.getPostId())
      .orElseThrow(NotFoundResourceException::new);

    return Comment.builder()
      .addTextContent(createCommentRequest.getTextContent())
      .addUser(user)
      .addPost(post)
      .build();
  }

}
