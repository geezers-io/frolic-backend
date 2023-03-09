package com.frolic.sns.post.dto.v2;

import com.frolic.sns.global.exception.BuilderArgumentNotValidException;
import com.frolic.sns.global.util.message.CommonMessageUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.frolic.sns.global.util.message.CommonMessageUtils.getIllegalFieldError;

@Getter
@NoArgsConstructor
public class UpdatePostRequest {

  private String textContent;
  private List<String> hashtags;
  private List<Long> imageIds;

  public UpdatePostRequest(String textContent, List<String> hashtags, List<Long> imageIds) {
    Assert.hasText(textContent, getIllegalFieldError("textContent"));
    Assert.notNull(hashtags, getIllegalFieldError("hashtags"));
    Assert.notNull(imageIds, getIllegalFieldError("imageIds"));
    this.textContent = textContent;
    this.hashtags = hashtags;
    this.imageIds = imageIds;
  }

}
