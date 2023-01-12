package com.frolic.sns.global.common;

import java.util.HashMap;
import java.util.Map;

public class ResponseHelper {

  /**
   * 클라이언트에 전달 할 객체를 { data: ... } 로 감 싼 테이블로 반환합니다.
   * @param data 클라이언트에 전달할 객체
   */
  public static <T> Map<String, T> createDataMap(T data) {
    Map<String, T> map = new HashMap<>();
    map.put("data", data);
    return map;
  }

}
