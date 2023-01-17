package com.frolic.sns.auth.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class OverTimeAuthCodeException extends CustomException {
  public OverTimeAuthCodeException() {
    super(ErrorCode.OVER_TIME_AUTH_CODE);
  }
}
