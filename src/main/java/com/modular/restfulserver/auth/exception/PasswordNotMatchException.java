package com.modular.restfulserver.auth.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class PasswordNotMatchException extends CustomException {
  public PasswordNotMatchException() {
    super(ErrorCode.PASSWORD_NOT_MATCH);
  }
}
