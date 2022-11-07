package com.modular.restfulserver.global.exception;

public class ErrorResponseWrapper {
  private final String message;

  public ErrorResponseWrapper(String message) {
    this.message = message;
  }
}
