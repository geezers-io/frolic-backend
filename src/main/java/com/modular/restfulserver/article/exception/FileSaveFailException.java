package com.modular.restfulserver.article.exception;

import com.modular.restfulserver.global.exception.CustomException;
import com.modular.restfulserver.global.exception.ErrorCode;

public class FileSaveFailException extends CustomException {

  public FileSaveFailException() {
    super(ErrorCode.FILE_SAVE_FAILURE);
  }

}
