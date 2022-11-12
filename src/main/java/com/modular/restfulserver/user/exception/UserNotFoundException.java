package com.modular.restfulserver.user.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class UserNotFoundException extends CustomException {
  public UserNotFoundException() {
    super(ErrorCode.NOT_FOUND_USER);
  }
}
