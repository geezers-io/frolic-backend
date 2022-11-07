package com.modular.restfulserver.auth.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class NoneTokenOnHeaderException extends CustomException {
  public NoneTokenOnHeaderException() {
    super(ErrorCode.NONE_TOKEN_ON_HEADER);
  }
}
