package com.modular.restfulserver.util;

import com.modular.restfulserver.user.model.User;

public enum TestUser {
  EUNGI(
    User.builder()
      .addUsername("galaxyhi4276")
      .addRealname("최은기")
      .addEmail("galaxyhi4276@gmail.com")
      .addPhoneNumber(TestUser.testPhoneNumber)
      .addPassword(TestUser.testPassword)
      .build()
  ),
  JUNEJAE(
    User.builder()
      .addUsername("bearbearbear")
      .addRealname("김준재")
      .addEmail("bear-bear-bear@kakao.com")
      .addPhoneNumber(TestUser.testPhoneNumber)
      .addPassword(TestUser.testPassword)
      .build()
  ),
  DONGJAE(
    User.builder()
      .addUsername("pandora2948")
      .addRealname("백동재")
      .addPhoneNumber(TestUser.testPhoneNumber)
      .addEmail("kgdj030@gmail.com")
      .addPassword(TestUser.testPassword)
      .build()
  ),
  JIYEON(
    User.builder()
      .addUsername("han05081486")
      .addRealname("한지연")
      .addPhoneNumber(TestUser.testPhoneNumber)
      .addEmail("han05081486@gmail.com")
      .addPassword(TestUser.testPassword)
      .build()
  ),
  ;

  public static final String testPassword = "@PerfectSns4276";
  public static final String testPhoneNumber = "010-3434-4343";

  private final User user;

  TestUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

}
