package com.frolic.sns.user.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class NotPermissionException extends CustomException {
  public NotPermissionException() {
    super(ErrorCode.NOT_PERMISSION);
  }
}
