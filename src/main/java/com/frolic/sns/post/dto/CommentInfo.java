package com.frolic.sns.post.dto;

import com.frolic.sns.post.model.Comment;
import com.frolic.sns.post.util.PostValidationMessages;
import com.frolic.sns.user.dto.UserInfo;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentInfo {

  private final Long id;

  private final Long postId;

  private final Long replyUserId;

  private final String textContent;

  private final UserInfo userInfo;

  @Builder(setterPrefix = "add")
  public CommentInfo(
    Long id,
    Long postId,
    Long replyUserId,
    String textContent,
    UserInfo userInfo
  ) {
    Assert.notNull(id, PostValidationMessages.notNullId);
    Assert.notNull(postId, PostValidationMessages.notNullPostId);
    Assert.notNull(textContent, PostValidationMessages.notNullTextContent);
    Assert.notNull(userInfo, PostValidationMessages.notNullUserInfo);

    this.id = id;
    this.postId = postId;
    this.replyUserId = replyUserId;
    this.textContent = textContent;
    this.userInfo = userInfo;
  }

  public static CommentInfo from(Comment comment) {
    return CommentInfo.builder()
      .addId(comment.getId())
      .addUserInfo(UserInfo.from(comment.getUser()))
      .addPostId(comment.getPost().getId())
      .addReplyUserId(comment.getReplyUserPkId())
      .addTextContent(comment.getTextContent())
      .build();
  }

}
