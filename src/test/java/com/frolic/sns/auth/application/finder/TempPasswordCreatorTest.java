package com.frolic.sns.auth.application.finder;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


class TempPasswordCreatorTest {
  protected TempPasswordCreator tempPasswordCreator = new TempPasswordCreator();

  @Test
  @DisplayName("임시 패스워드를 1000회 생성하고 정규식에 맞는 지 테스트한다.")
  void createAndPrintTempPassword() {
    String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$";
    Pattern pattern = Pattern.compile(regex);
    for (int i = 0; i < 1000; i++) {
      String generatedPassword = tempPasswordCreator.create();
      System.out.println(i + " count: " + generatedPassword);
      Assertions.assertThat(generatedPassword.length()).isEqualTo(15);
      Matcher matcher = pattern.matcher(generatedPassword);
      Assertions.assertThat(matcher.matches()).isTrue();
    }
  }

}