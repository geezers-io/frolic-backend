package com.modular.restfulserver.global.exception;

public class NotFoundResourceException extends CustomException {
  public NotFoundResourceException() {
    super(ErrorCode.NOT_FOUND_RESOURCE);
  }
}
