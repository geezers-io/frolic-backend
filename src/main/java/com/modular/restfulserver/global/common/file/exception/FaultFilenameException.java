package com.modular.restfulserver.global.common.file.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class FaultFilenameException extends CustomException {

  public FaultFilenameException() {
    super(ErrorCode.FAULT_FILENAME_REQUEST);
  }

}
