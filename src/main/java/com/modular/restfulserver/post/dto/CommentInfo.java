package com.modular.restfulserver.post.dto;

import com.modular.restfulserver.post.model.Comment;
import com.modular.restfulserver.post.util.PostValidationMessages;
import com.modular.restfulserver.user.dto.UserDetail;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDetail {

  private final Long id;

  private final Long postId;

  private final Long replyUserId;

  private final String textContent;

  private final UserDetail userDetail;

  @Builder(setterPrefix = "add")
  public CommentDetail(
    Long id,
    Long postId,
    Long replyUserId,
    String textContent,
    UserDetail userDetail
  ) {
    Assert.notNull(id, PostValidationMessages.notNullId);
    Assert.notNull(postId, PostValidationMessages.notNullPostId);
    Assert.notNull(textContent, PostValidationMessages.notNullTextContent);
    Assert.notNull(userDetail, PostValidationMessages.notNullUserInfo);

    this.id = id;
    this.postId = postId;
    this.replyUserId = replyUserId;
    this.textContent = textContent;
    this.userDetail = userDetail;
  }

  public static CommentDetail from(Comment comment) {
    return CommentDetail.builder()
      .addId(comment.getId())
      .addUserDetail(UserDetail.from(comment.getUser()))
      .addPostId(comment.getPost().getId())
      .addReplyUserId(comment.getReplyUserPkId())
      .addTextContent(comment.getTextContent())
      .build();
  }

}
