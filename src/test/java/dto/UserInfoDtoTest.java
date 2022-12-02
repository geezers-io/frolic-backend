package unit.restfulserver.user.dto;

import com.modular.restfulserver.user.dto.UserInfoDto;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoDtoTest {

  UserInfoForClientDto mockUserInfoForClient = UserInfoForClientDto.builder()
    .addUserId(0L)
    .addUsername("mock")
    .addEmail("mock@mockito.com")
    .addRealname("mockReal")
    .build();

  @Test
  @DisplayName("[Builder Argument Test] 빌더 패턴으로 생성 시 필드를 제외할 때 예외 발생")
  void noAllPostCount() {
    assertThrows(IllegalArgumentException.class, () -> {
      UserInfoDto.builder()
        .addUserInfo(mockUserInfoForClient)
        .addAllGivenLikeCount(0L)
        .addAllFollowingCount(0L)
        .addAllFollowerCount(0L)
//        .addAllPostCount(0L)
        .build();
    });
  }

}
