package com.frolic.sns.post.application.v2;

import com.frolic.sns.global.exception.NotFoundResourceException;
import com.frolic.sns.post.dto.CommentInfo;
import com.frolic.sns.post.model.Comment;
import com.frolic.sns.post.repository.CommentDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentManager {

  private final CommentDslRepository commentDslRepository;

  public Comment getComment(Long commentId) {
    return commentDslRepository.getComment(commentId).orElseThrow(NotFoundResourceException::new);
  }

  public CommentInfo getCommentInfo(Long commentId) {
    return CommentInfo.from(getComment(commentId));
  }

}
