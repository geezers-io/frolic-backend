package com.frolic.sns.global.common.file.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class FaultFileExtensionException extends CustomException {

  public FaultFileExtensionException() {
    super(ErrorCode.FAULT_FILE_EXTENSION_REQUEST);
  }

}
