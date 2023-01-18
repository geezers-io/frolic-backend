package com.frolic.sns.global.exception;

public class NotFoundCookieException extends CustomException {
  public NotFoundCookieException() {
    super(ErrorCode.NOT_FOUND_COOKIE);
  }
}
