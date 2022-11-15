package com.modular.restfulserver.article.dto;

import com.modular.restfulserver.global.exception.BuilderArgumentNotValidException;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
public class CreateCommentRequestDto {

  @NotNull
  private final Long postId;

  private final Long replyUserId;

  @NotNull
  private final Long ownerId;

  @NotNull
  @Max(value = 500, message = "본문 데이터는 500자 이하여야 합니다.")
  private final String textContent;

  @Builder(setterPrefix = "add")
  public CreateCommentRequestDto(
    Long postId,
    Long replyUserId,
    Long ownerId,
    String textContent
  ) {
    if (postId == null
      || ownerId == null
      || textContent == null
    )
      throw new BuilderArgumentNotValidException("CreateCommentRequestDto 빌더 인자가 잘못되었습니다.");

    this.postId = postId;
    this.replyUserId = replyUserId;
    this.ownerId = ownerId;
    this.textContent = textContent;
  }

}
