package com.modular.restfulserver.article.dto;

import com.modular.restfulserver.article.model.Article;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;

@Getter
public class CreatePostRequestDto {

//  @NotEmpty
//  @Max(value = 1000, message = "게시글 본문은 1000글자 미만이어야 합니다.")
  private final String textContent;
  // TODO: 2022-11-13 Validation 제대로 작성하기 
  private final List<String> hashtags;

  @Builder(setterPrefix = "add")
  public CreatePostRequestDto(
    String textContent,
    List<String> hashtags
  ) {
    Assert.hasText(textContent, "textContent field must be not empty");
    this.textContent = textContent;
    this.hashtags = hashtags;
  }

  public Article toEntity() {
    return Article.builder()
      .addTextContent(textContent)
      .build();
  }

}
