package com.modular.restfulserver.user.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class AlreadyExistsFollowException extends CustomException {
  public AlreadyExistsFollowException() {
    super(ErrorCode.ALREADY_FOLLOW_INFO);
  }
}
