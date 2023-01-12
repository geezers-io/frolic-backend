package com.frolic.sns.auth.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class AlreadyExistsUserException extends CustomException {
  public AlreadyExistsUserException() {
    super(ErrorCode.ALREADY_EXISTS_USER);
  }
}
