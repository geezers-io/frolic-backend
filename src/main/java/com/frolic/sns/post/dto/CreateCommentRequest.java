package com.frolic.sns.post.dto;

import com.frolic.sns.post.util.PostValidationMessages;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateCommentRequest {

  @NotNull
  private Long postId;

  @NotNull // TODO: 2022-11-16 validation
  private String textContent;


  @Builder(setterPrefix = "add")
  public CreateCommentRequest(
    Long postId,
    String textContent
  ) {
    Assert.notNull(postId, PostValidationMessages.notNullPostId);
    Assert.notNull(textContent, PostValidationMessages.notNullPostId);
    this.postId = postId;
    this.textContent = textContent;
  }

}
