package com.frolic.sns.auth.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class NoneTokenOnHeaderException extends CustomException {
  public NoneTokenOnHeaderException() {
    super(ErrorCode.NONE_TOKEN_ON_HEADER);
  }
}
