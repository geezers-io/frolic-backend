package com.frolic.sns.auth.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class InvalidTokenException extends CustomException {
  public InvalidTokenException() {
    super(ErrorCode.INVALID_TOKEN);
  }
}
