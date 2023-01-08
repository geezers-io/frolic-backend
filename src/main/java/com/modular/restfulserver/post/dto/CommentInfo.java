package com.modular.restfulserver.post.dto;

import com.modular.restfulserver.post.model.Comment;
import com.modular.restfulserver.post.util.ValidationMessages;
import com.modular.restfulserver.user.dto.UserInfo;
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
    Assert.notNull(id, ValidationMessages.notNullId);
    Assert.notNull(postId, ValidationMessages.notNullPostId);
    Assert.notNull(textContent, ValidationMessages.notNullTextContent);
    Assert.notNull(userInfo, ValidationMessages.notNullUserInfo);

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
