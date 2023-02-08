package com.frolic.sns.post.dto.v2;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.validation.constraints.Max;
import java.util.List;

import static com.frolic.sns.global.util.message.CommonMessageUtils.getIllegalFieldError;

@Getter
@NoArgsConstructor
public class CreatePostRequest {

//  @Max(value = 150, message = "게시글 본문은 150 글자 이하여야 합니다.")
  private String textContent;

  private List<String> hashtags;

  private List<Long> imageIds;

  @Builder(setterPrefix = "add")
  public CreatePostRequest(
    String textContent,
    List<String> hashtags,
    List<Long> imageIds
  ) {
    Assert.hasText(textContent, getIllegalFieldError("textContent"));
    this.textContent = textContent;
    this.hashtags = hashtags;
    this.imageIds = imageIds;
  }

}
