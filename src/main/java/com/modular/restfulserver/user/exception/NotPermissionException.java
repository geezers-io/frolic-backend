package com.modular.restfulserver.user.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class NotPermissionException extends CustomException {
  public NotPermissionException() {
    super(ErrorCode.NOT_PERMISSION);
  }
}
