package com.modular.restfulserver.post.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.MockProvider;

import static org.junit.jupiter.api.Assertions.*;

class CommentDetailTest {

  @Test
  @DisplayName("id 값이 null 이면 에러가 발생한다.")
  void checkIdValueIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      CommentDetail.builder()
        .addPostId(1L)
        .addTextContent("test")
        .addUserDetail(MockProvider.mockUserDetailForClientDto)
        .build();
    });
  }

  @Test
  @DisplayName("postId 값이 null 이면 에러가 발생한다.")
  void checkPostIdValueIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      CommentDetail.builder()
        .addId(1L)
        .addTextContent("test")
        .addUserDetail(MockProvider.mockUserDetailForClientDto)
        .build();
    });
  }

  @Test
  @DisplayName("tetContent 값이 null 이면 에러가 발생한다.")
  void checkTextContentValueIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      CommentDetail.builder()
        .addId(1L)
        .addPostId(1L)
        .addUserDetail(MockProvider.mockUserDetailForClientDto)
        .build();
    });
  }

  @Test
  @DisplayName("userInfo 값이 null 이면 에러가 발생한다.")
  void checkUserInfoValueIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      CommentDetail.builder()
        .addId(1L)
        .addPostId(1L)
        .addTextContent("text")
        .build();
    });
  }

}