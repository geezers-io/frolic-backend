package com.modular.restfulserver.global.common.file.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class FileDownloadFailureException extends CustomException {

  public FileDownloadFailureException() {
    super(ErrorCode.FILE_DOWNLOAD_FAILURE);
  }

}
