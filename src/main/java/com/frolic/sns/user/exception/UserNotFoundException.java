package com.frolic.sns.user.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class UserNotFoundException extends CustomException {
  public UserNotFoundException() {
    super(ErrorCode.NOT_FOUND_USER);
  }
}
