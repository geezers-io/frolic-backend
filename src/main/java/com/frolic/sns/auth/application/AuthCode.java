package com.frolic.sns.auth.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @implNote 인증 코드에 대한 객체입니다.
 */
@RequiredArgsConstructor
@Getter
public class AuthCode {
  private final String code;
  private final FinderType finderType;

  private final int countOfAttempts = 0;

  @NoArgsConstructor
  @Getter
  public static class MetaData implements Serializable {
    private FinderType finderType;
    private int countOfAttempts;

    public MetaData(FinderType type, int countOfAttempts) {
      this.finderType = type;
      this.countOfAttempts = countOfAttempts;
    }

    public void tryVerification() {
      countOfAttempts++;
    }

  }

  public MetaData getAuthCodeMetaData() {
    return new MetaData(finderType, countOfAttempts);
  }


}
