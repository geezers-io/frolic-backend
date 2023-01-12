package com.frolic.sns.global.common.file.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class FaultFilenameException extends CustomException {

  public FaultFilenameException() {
    super(ErrorCode.FAULT_FILENAME_REQUEST);
  }

}
