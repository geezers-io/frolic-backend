package com.modular.restfulserver.user.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.MockData;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsTest {

  @Test
  @DisplayName("빌더 패턴으로 생성 시 필드를 제외할 때 예외 발생")
  void noAllPostCount() {
    assertThrows(IllegalArgumentException.class, () -> {
      UserUnitedDetails.builder()
        .addUserInfo(MockData.mockUserDetailsForClientDto)
        .addAllGivenLikeCount(0L)
        .addAllFollowingCount(0L)
        .addAllFollowerCount(0L)
//        .addAllPostCount(0L)
        .build();
    });
  }

}