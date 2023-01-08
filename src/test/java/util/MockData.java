package utils;

import com.modular.restfulserver.user.dto.UserInfo;

import java.time.LocalDateTime;

public class MockData {
  public static UserInfo mockUserInfoForClientDto = UserInfo.builder()
    .addUsername("username")
    .addRealname("realname")
    .addEmail("galaxy4276@gmail.com")
    .addUpdatedDate(LocalDateTime.now())
    .addCreatedDate(LocalDateTime.now())
    .addId(1L)
    .build();

}
