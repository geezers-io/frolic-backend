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
  private final String filename;

  @Builder(setterPrefix = "add")
  public FileInfo(final Long id, final String downloadUrl, final String filename) {
    Assert.isInstanceOf(Long.class, id, getIllegalFieldError("id"));
    Assert.hasText(downloadUrl, getIllegalFieldError("downloadUrl"));
    Assert.hasText(filename, getIllegalFieldError("filename"));
    this.id = id;
    this.downloadUrl = downloadUrl;
    this.filename = filename;
  }

}
