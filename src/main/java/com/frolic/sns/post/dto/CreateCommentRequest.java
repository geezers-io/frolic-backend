package com.frolic.sns.post.dto;

import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import static com.frolic.sns.global.util.message.CommonMessageUtils.getIllegalFieldError;

@Getter
@NoArgsConstructor
public class CreateCommentRequest {

  @NotNull
  private Long postId;

  @NotNull
  @Max(value = 140, message = "텍스트 길이는 150자 미만이어야 합니다.")
  private String textContent;

  @Builder(setterPrefix = "add")
  public CreateCommentRequest(Long postId, String textContent) {
    Assert.notNull(postId, getIllegalFieldError("postId"));
    Assert.notNull(textContent, getIllegalFieldError("textContent"));
    this.postId = postId;
    this.textContent = textContent;
  }

}
