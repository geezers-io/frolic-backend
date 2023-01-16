package com.frolic.sns.auth.application.finder;

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
  private final String destination; // 인증되었을 때 사용자 정보에 대해 전달할 정보입니다.

  private final int countOfAttempts = 0;

  @NoArgsConstructor
  @Getter
  public static class MetaData implements Serializable {
    private FinderType finderType;
    private int countOfAttempts;

    private String destination;

    public MetaData(FinderType type, int countOfAttempts, String destination) {
      this.finderType = type;
      this.countOfAttempts = countOfAttempts;
      this.destination = destination;
    }

    public void tryVerification() {
      countOfAttempts++;
    }

  }

  public MetaData getAuthCodeMetaData() {
    return new MetaData(finderType, countOfAttempts, destination);
  }

}