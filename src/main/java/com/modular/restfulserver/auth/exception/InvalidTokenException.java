package com.modular.restfulserver.auth.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class InvalidTokenException extends CustomException {
  public InvalidTokenException() {
    super(ErrorCode.INVALID_TOKEN);
  }
}
