package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.dto.CreateCommentRequest;
import com.modular.restfulserver.post.dto.CommentDetails;
import com.modular.restfulserver.post.model.Post;
import com.modular.restfulserver.post.model.Comment;
import com.modular.restfulserver.post.repository.PostRepository;
import com.modular.restfulserver.post.repository.CommentRepository;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.global.exception.NotFoundResourceException;
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
  private final PostRepository postRepository;

  @Override
  public CommentDetails getCommentById(Long commentId) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundResourceException::new);
    return CommentDetails.from(comment);
  }

  @Override
  public List<CommentDetails> getCommentsByArticlePagination(Long articleId, Pageable pageable) {
    Post post = postRepository.findById(articleId).orElseThrow(NotFoundResourceException::new);
    Page<Comment> commentPage = commentRepository.findAllByPostOrderByCreatedDate(post, pageable);

    return commentPage.stream()
      .map(CommentDetails::from)
      .collect(Collectors.toList());
  }

  @Override
  public List<CommentDetails> getCommentsByUserPagination(String username, Pageable pageable) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(UserNotFoundException::new);

    Page<Comment> commentPage = commentRepository.findAllByUserOrderByCreatedDate(user, pageable);

    return commentPage.stream()
      .map(CommentDetails::from)
      .collect(Collectors.toList());
  }

  @Override
  public CommentDetails createComment(String token, CreateCommentRequest dto) {
    Comment newComment = getCommentByCreateRequestDto(dto);
    commentRepository.save(newComment);
    return CommentDetails.from(newComment);
  }

  @Override
  public CommentDetails updateComment(String token, CreateCommentRequest createCommentInfo, Long commentId) {
    User tokenUser = getUserIsTokenAble(token);
    boolean isSameUser = Objects.equals(tokenUser.getId(), createCommentInfo.getPostOwnerId());
    if (!isSameUser)
      throw new NotPermissionException();
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(NotFoundResourceException::new);

    comment.updateTextContent(createCommentInfo.getTextContent());
    commentRepository.save(comment);
    return CommentDetails.from(comment);
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

  private User getUserIsTokenAble(String token) {
    return userRepository.findByEmail(
      jwtProvider.getUserEmailByToken(token)
    ).orElseThrow(UserNotFoundException::new);
  }

  private Comment getCommentByCreateRequestDto(CreateCommentRequest dto) {
    User user = userRepository.findById(dto.getPostOwnerId())
      .orElseThrow(UserNotFoundException::new);
    Post post = postRepository.findById(dto.getPostId())
      .orElseThrow(NotFoundResourceException::new);

    return Comment.builder()
      .addTextContent(dto.getTextContent())
      .addReplyUserPkId(dto.getReplyUserId())
      .addUser(user)
      .addPost(post)
      .build();
  }

}
