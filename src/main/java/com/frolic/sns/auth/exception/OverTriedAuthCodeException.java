package com.frolic.sns.auth.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class OverTriedAuthCodeException extends CustomException {
  public OverTriedAuthCodeException() {
    super(ErrorCode.OVER_TRIED_AUTH_CODE);
  }
}
