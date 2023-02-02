package com.frolic.sns.post.dto;

import com.frolic.sns.post.model.Post;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.validation.constraints.Max;
import java.util.List;

import static com.frolic.sns.global.util.message.CommonMessageUtils.getIllegalFieldError;

@Getter
public class CreatePostRequestV2 {

  @Max(value = 150, message = "게시글 본문은 1000글자 미만이어야 합니다.")
  private final String textContent;

  private final List<String> hashtags;

  private final List<Long> imageIds;

  @Builder(setterPrefix = "add")
  public CreatePostRequestV2(
    String textContent,
    List<String> hashtags,
    List<Long> imageIds
  ) {
    Assert.hasText(textContent, getIllegalFieldError("textContent"));
    Assert.notNull(imageIds, getIllegalFieldError("imageIds"));
    this.textContent = textContent;
    this.hashtags = hashtags;
    this.imageIds = imageIds;
  }

  public Post toEntity() {
    return Post.builder()
      .addTextContent(textContent)
      .build();
  }

}
