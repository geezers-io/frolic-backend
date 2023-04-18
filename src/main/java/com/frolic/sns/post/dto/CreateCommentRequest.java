package com.frolic.sns.post.dto;

import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.frolic.sns.global.util.message.CommonMessageUtils.getIllegalFieldError;

@Getter
@NoArgsConstructor
public class CreateCommentRequest {

  @NotNull
  private Long postId;

  @NotBlank
  @Size(min = 1, max = 140)
  private String textContent;

  @Builder(setterPrefix = "add")
  public CreateCommentRequest(Long postId, String textContent) {
    Assert.notNull(postId, getIllegalFieldError("postId"));
    Assert.notNull(textContent, getIllegalFieldError("textContent"));
    this.postId = postId;
    this.textContent = textContent;
  }

}
