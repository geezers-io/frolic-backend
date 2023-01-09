package com.modular.restfulserver.post.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.MockData;

import static org.junit.jupiter.api.Assertions.*;

class CommentInfoTest {

  @Test
  @DisplayName("id 값이 null 이면 에러가 발생한다.")
  void checkIdValueIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      CommentInfo.builder()
        .addPostId(1L)
        .addTextContent("test")
        .addUserInfo(MockData.mockUserInfoForClientDto)
        .build();
    });
  }

  @Test
  @DisplayName("postId 값이 null 이면 에러가 발생한다.")
  void checkPostIdValueIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      CommentInfo.builder()
        .addId(1L)
        .addTextContent("test")
        .addUserInfo(MockData.mockUserInfoForClientDto)
        .build();
    });
  }

  @Test
  @DisplayName("tetContent 값이 null 이면 에러가 발생한다.")
  void checkTextContentValueIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      CommentInfo.builder()
        .addId(1L)
        .addPostId(1L)
        .addUserInfo(MockData.mockUserInfoForClientDto)
        .build();
    });
  }

  @Test
  @DisplayName("userInfo 값이 null 이면 에러가 발생한다.")
  void checkUserInfoValueIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      CommentInfo.builder()
        .addId(1L)
        .addPostId(1L)
        .addTextContent("text")
        .build();
    });
  }

}