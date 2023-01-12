package com.frolic.sns.auth.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class PasswordNotMatchException extends CustomException {
  public PasswordNotMatchException() {
    super(ErrorCode.PASSWORD_NOT_MATCH);
  }
}
