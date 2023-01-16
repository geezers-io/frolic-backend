package com.frolic.sns.auth.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class MisMatchFinderTypeException extends CustomException {
  public MisMatchFinderTypeException() { super(ErrorCode.MISMATCH_FINDER_TYPE);  }
}
