package com.frolic.sns.global.common.file.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class FileSaveFailException extends CustomException {

  public FileSaveFailException() {
    super(ErrorCode.FILE_SAVE_FAILURE);
  }

}
