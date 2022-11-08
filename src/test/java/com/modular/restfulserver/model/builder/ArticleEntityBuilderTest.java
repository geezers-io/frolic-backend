package com.modular.restfulserver.model.builder;

import static org.junit.jupiter.api.Assertions.*;

import com.modular.restfulserver.article.model.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArticleEntityBuilderTest {
@Test
  @DisplayName("게시글 제목(title) 필드가 비어 있으면 예외처리 된다.")
  public void Article_title_empty_ex() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> {
      Article.builder()
        .addTitle("")
        .addTextContent("hello")
        .build();
    });
    assertTrue(ex.getMessage().contains("title field must be string"));
  }

  @Test
  @DisplayName("게시글 내용(content)가 비어있어도 생성가능하다.")
  public void Article_content_empty_not_ex() {
    Article.builder()
      .addTitle("게시글임둥")
      .addTextContent("")
      .build();
  }

}
