package com.frolic.sns.user.dto;

import com.frolic.sns.util.MockProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailTest {

  @Test
  @DisplayName("빌더 패턴으로 생성 시 필드를 제외할 때 예외 발생")
  void noAllPostCount() {
    assertThrows(IllegalArgumentException.class, () -> {
      UserUnitedInfo.builder()
        .addUserInfo(MockProvider.mockUserInfoForClientDto)
        .addAllGivenLikeCount(0L)
        .addAllFollowingCount(0L)
        .addAllFollowerCount(0L)
//        .addAllPostCount(0L)
        .build();
    });
  }

}