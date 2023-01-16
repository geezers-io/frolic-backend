package com.frolic.sns.global.common.file.exception;

import com.frolic.sns.global.exception.CustomException;
import com.frolic.sns.global.exception.ErrorCode;

public class FileDownloadFailureException extends CustomException {

  public FileDownloadFailureException() {
    super(ErrorCode.FILE_DOWNLOAD_FAILURE);
  }

}
