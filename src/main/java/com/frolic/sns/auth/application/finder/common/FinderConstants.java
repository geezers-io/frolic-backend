package com.frolic.sns.auth.application.finder.common;

public class FinderConstants {
  public static final int EXPIRE_MINUTES = 5;
  public static final int MAX_TRY_COUNT = 5;

  public static final String EMAIL_AUTHCODE_SEND_MESSAGE =
    "[FrolicSNS] 이메일 찾기를 위한 인증코드입니다.\n";

  public static final String PASSWORD_AUTHCODE_SEND_MESSAGE =
          "[FrolicSNS] 임시 비밀번호 생성을 위한 인증코드입니다.\n";
}
