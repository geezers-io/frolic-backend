package com.frolic.sns.user.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class AlreadyExistsFollowException extends CustomException {
  public AlreadyExistsFollowException() {
    super(ErrorCode.ALREADY_FOLLOW_INFO);
  }
}
