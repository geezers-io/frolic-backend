package com.frolic.sns.post.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class AlreadyExistsLikeException extends CustomException {
  public AlreadyExistsLikeException() {
    super(ErrorCode.ALREADY_LIKE_INFO);
  }
}
