package com.frolic.sns.global.common.file.model;

import com.frolic.sns.global.common.file.application.CustomFile;
import com.frolic.sns.global.util.message.CommonMessageUtils;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.user.model.User;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity(name = "files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplicationFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO: 2022-11-25 키 공부 좀 더 해보고 적용해보기 
//  @Column(unique = true)
  private String name;

  private Long size;

  @Value("${server.address}")
  private String HOST;

  @Value("${server.port}")
  private String PORT;

  @Deprecated
  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  // TODO: 2022-11-24 안정성 수정 필요 
  @Builder(setterPrefix = "add")
  public ApplicationFile(String name, Long size) {
    Assert.hasText(name, CommonMessageUtils.getIllegalFieldError("name"));
    Assert.isInstanceOf(Long.class, size, CommonMessageUtils.getIllegalFieldError("size"));
    this.name = name;
    this.size = size;
  }

  @Deprecated
  public static ApplicationFile createFileByCustomFile(CustomFile file, Post post) {
    MultipartFile multipartFile = file.getFile();
    return com.frolic.sns.global.common.file.model.ApplicationFile.builder()
      .addName(file.getCustomFilename())
      .addSize(multipartFile.getSize())
      .build();
  }

  @Deprecated
  public String getDownloadUrl() {
    return HOST + ":" + PORT + "/api/download/" + name;
  }

}
