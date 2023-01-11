package com.modular.restfulserver.global.util.message;

public class CommonMessageUtils {

  public static String getIllegalFieldError(String field) {
    return field + "값이 초기화되지 않았습니다. 초기화 부분 재확인해주세요.";
  }

}
