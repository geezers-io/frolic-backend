package com.frolic.sns.global.exception;

public class BuilderArgumentNotValidException extends CustomMessageException {

  public BuilderArgumentNotValidException(String message) {
    super("[BuilderArgumentNotValidException]: " + message);
  }

}
