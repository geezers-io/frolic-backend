package com.modular.restfulserver.global.common.file.application;

import com.modular.restfulserver.global.common.file.exception.FaultFileExtensionException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class CustomFile {
  private final MultipartFile file;
  private final String customFilename;

  @Value("server.address")
  private String HOST;

  @Value("server.port")
  private String PORT;

  public static String parseFilenameByDownloadUrls(String downloadUrl) {
    String[] chunks = downloadUrl.split("/");
    return chunks[chunks.length - 1]; // return filename
  }

  public CustomFile(MultipartFile file) {
    this.file = file;
    String filename = file.getOriginalFilename();
    // TODO: 2022-11-25 NPE 처리에 관해 조사 필요해보임 
    int splitIndex = filename.indexOf(".");
    if (splitIndex == -1)
      throw new FaultFileExtensionException();
    String ext = filename.substring(splitIndex);
    this.customFilename = filename.substring(0, splitIndex) + "_" + UUID.randomUUID() + System.currentTimeMillis() + ext;
  }

  public String getDownloadUrl() {
    return HOST + ":" + PORT + "/api/download/" + customFilename;
  }

}
