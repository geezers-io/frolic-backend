package com.modular.restfulserver.auth.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class AlreadyExistsUserException extends CustomException {
  public AlreadyExistsUserException() {
    super(ErrorCode.ALREADY_EXISTS_USER);
  }
}
