package com.frolic.sns.auth.application.finder;

import com.frolic.sns.global.util.message.CommonMessageUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalTime;
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

  private final LocalTime localTime;

  private final int countOfAttempts;

  @Builder(setterPrefix = "add")
  public AuthCode(
    UUID id,
    String code,
    int countOfAttempts,
    FinderType finderType,
    String destination,
    LocalTime localTime
  ) {
    Assert.notNull(id, CommonMessageUtils.getIllegalFieldError("id"));
    Assert.notNull(code, CommonMessageUtils.getIllegalFieldError("code"));
    Assert.notNull(finderType, CommonMessageUtils.getIllegalFieldError("finderType"));
    Assert.notNull(destination, CommonMessageUtils.getIllegalFieldError("destination"));
    Assert.isInstanceOf(LocalTime.class, localTime, CommonMessageUtils.getIllegalFieldError("localTime"));

    this.id = id;
    this.code = code;
    this.countOfAttempts = countOfAttempts;
    this.finderType = finderType;
    this.destination = destination;
    this.localTime = localTime;
  }

  @NoArgsConstructor
  @Getter
  public static class MetaData implements Serializable {

    private String code;

    private FinderType finderType;
    private int countOfAttempts;

    private String destination;


    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime localTime;

    @Builder(setterPrefix = "add")
    public MetaData(String code, FinderType type, int countOfAttempts, String destination, LocalTime localTime) {
      Assert.hasText(code, CommonMessageUtils.getIllegalFieldError("code"));
      Assert.hasText(destination, CommonMessageUtils.getIllegalFieldError("destination"));
      Assert.isInstanceOf(LocalTime.class, localTime, CommonMessageUtils.getIllegalFieldError("localTime"));

      this.code = code;
      this.finderType = type;
      this.countOfAttempts = countOfAttempts;
      this.destination = destination;
      this.localTime = localTime;
    }

    public void tryVerification() {
      countOfAttempts++;
    }

  }

  public MetaData getAuthCodeMetaData() {
    return MetaData.builder()
      .addCode(code)
      .addType(finderType)
      .addLocalTime(localTime)
      .addDestination(destination)
      .addCountOfAttempts(countOfAttempts)
      .build();
  }

  public static AuthCode fromMetadata(UUID id, MetaData metaData) {
    return AuthCode.builder()
      .addId(id)
      .addCode(metaData.getCode())
      .addFinderType(metaData.getFinderType())
      .addDestination(metaData.destination)
      .addCountOfAttempts(metaData.getCountOfAttempts())
      .addLocalTime(metaData.getLocalTime())
      .build();
  }

  public static AuthCode createAuthCode(UUID id, String code, FinderType finderType, String destination) {
    LocalTime expiredTime = LocalTime.now().plusMinutes(FinderConstants.EXPIRE_MINUTES);
    return AuthCode.builder()
      .addId(id)
      .addCode(code)
      .addDestination(destination)
      .addCountOfAttempts(0)
      .addLocalTime(expiredTime)
      .addFinderType(finderType)
      .build();
  }

}
