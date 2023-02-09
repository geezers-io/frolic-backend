package com.frolic.sns.auth.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class MisMatchAuthCodeException extends CustomException {
  public MisMatchAuthCodeException() {
    super(ErrorCode.MISMATCH_AUTH_CODE);
  }
}
