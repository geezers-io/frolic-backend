package com.frolic.sns.post.dto.v2;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.frolic.sns.global.util.message.CommonMessageUtils.getIllegalFieldError;

@Getter
@NoArgsConstructor
public class CreatePostRequest {

  @Size(max = 140, message = "게시글 본문은 140글자 이하여야 합니다.")
  private String textContent;

  @NotNull(message = "hashtags 값이 null 이어서는 안됩니다.")
  private List<String> hashtags;

  @NotNull(message = "imageIds 값이 null 이어서는 안됩니다.")
  private List<Long> imageIds;

  @Builder(setterPrefix = "add")
  public CreatePostRequest(
    String textContent,
    List<String> hashtags,
    List<Long> imageIds
  ) {
    Assert.hasText(textContent, getIllegalFieldError("textContent"));
    Assert.notNull(hashtags, getIllegalFieldError("hashtags"));
    this.textContent = textContent;
    this.hashtags = hashtags;
    this.imageIds = imageIds;
  }

}
