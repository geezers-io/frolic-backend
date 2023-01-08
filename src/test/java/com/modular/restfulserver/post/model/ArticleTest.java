package com.modular.restfulserver.post.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleTest {

  @Test
  @DisplayName("게시글 내용(content)가 비어있어도 생성가능하다.")
  public void Article_content_empty_not_ex() {
    Post.builder()
      .addTextContent("")
      .build();
  }

}