package com.frolic.sns.auth.application.finder;

import com.frolic.sns.global.util.message.CommonMessageUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * @implNote 인증 코드에 대한 객체입니다.
 */
@Getter
public class AuthCode {
  private final UUID id;
  private final String code;
  private final FinderType finderType;
  private final String destination; // 인증되었을 때 사용자 정보에 대해 전달할 정보입니다.

  private final int countOfAttempts = 0;

  @Builder(setterPrefix = "add")
  public AuthCode(UUID id, String code, FinderType finderType, String destination) {
    Assert.notNull(id, CommonMessageUtils.getIllegalFieldError("id"));
    Assert.notNull(code, CommonMessageUtils.getIllegalFieldError("code"));
    Assert.notNull(finderType, CommonMessageUtils.getIllegalFieldError("finderType"));
    Assert.notNull(destination, CommonMessageUtils.getIllegalFieldError("destination"));
    this.id = id;
    this.code = code;
    this.finderType = finderType;
    this.destination = destination;
  }

  @NoArgsConstructor
  @Getter
  public static class MetaData implements Serializable {
    private String code;

    private FinderType finderType;
    private int countOfAttempts;

    private String destination;

    @Builder(setterPrefix = "add")
    public MetaData(String code, FinderType type, int countOfAttempts, String destination) {
      Assert.hasText(code, CommonMessageUtils.getIllegalFieldError("code"));
      Assert.hasText(destination, CommonMessageUtils.getIllegalFieldError("destination"));

      this.code = code;
      this.finderType = type;
      this.countOfAttempts = countOfAttempts;
      this.destination = destination;
    }

    public void tryVerification() {
      countOfAttempts++;
    }

  }

  public MetaData getAuthCodeMetaData() {
    return new MetaData(code, finderType, countOfAttempts, destination);
  }

}
