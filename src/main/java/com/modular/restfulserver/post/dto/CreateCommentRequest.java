package com.modular.restfulserver.post.dto;

import static com.modular.restfulserver.global.utils.message.FieldError.*;
import com.modular.restfulserver.post.util.ValidationMessages;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateCommentRequest {

  @NotNull
  private final Long postId;

  private final Long replyUserId;

  private final Long postOwnerId;

  @NotNull // TODO: 2022-11-16 validation 
//  @Max(value = 500, message = "본문 데이터는 500자 이하여야 합니다.")
  private final String textContent;


  @Builder(setterPrefix = "add")
  public CreateCommentRequest(
    Long postId,
    Long replyUserId,
    String textContent,
    Long postOwnerId
  ) {
    Assert.notNull(postId, ValidationMessages.notNullPostId);
    Assert.notNull(textContent, ValidationMessages.notNullPostId);
    Assert.notNull(postOwnerId, ValidationMessages.notNullOwnerId);
    this.postId = postId;
    this.replyUserId = replyUserId;
    this.textContent = textContent;
    this.postOwnerId = postOwnerId;
  }

}
