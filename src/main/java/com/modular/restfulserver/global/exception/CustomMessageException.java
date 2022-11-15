package com.modular.restfulserver.global.exception;

public class CustomMessageException extends RuntimeException {
  private final String errorMessage;

  public CustomMessageException(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

}
