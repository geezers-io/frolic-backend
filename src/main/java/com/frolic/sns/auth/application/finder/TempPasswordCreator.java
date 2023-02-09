package com.frolic.sns.auth.application.finder;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TempPasswordCreator {
  private final int startIndex = 97;
  private final int endIndex = 122;
  private final int[] specialCharIndexes = { 33, 35, 64, 36 };

  public String create() {
    Random random = new Random();
    random.setSeed(System.currentTimeMillis());
    char special = (char)specialCharIndexes[(int)(Math.random() * (specialCharIndexes.length - 1))];
    int digits = (int)(Math.random() * (99 - 10 + 1)) + 10;
    String alphabets = random.ints(startIndex, endIndex)
      .limit(12)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
    return special + alphabets + digits;
  }

}
