package com.modular.restfulserver.file.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class FaultFileExtensionException extends CustomException {

  public FaultFileExtensionException() {
    super(ErrorCode.FAULT_FILE_EXTENSION_REQUEST);
  }

}
