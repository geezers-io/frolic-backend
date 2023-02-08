package com.frolic.sns.global.common.file.dto;

import com.frolic.sns.global.util.message.CommonMessageUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

import static com.frolic.sns.global.util.message.CommonMessageUtils.getIllegalFieldError;

@Getter
public class FileInfo {

  private final Long id;
  private final String downloadUrl;

  @Builder(setterPrefix = "add")
  public FileInfo(final Long id, final String downloadUrl) {
    Assert.isInstanceOf(Long.class, id, getIllegalFieldError("id"));
    Assert.hasText(downloadUrl, getIllegalFieldError("downloadUrl"));
    this.id = id;
    this.downloadUrl = downloadUrl;
  }

}
