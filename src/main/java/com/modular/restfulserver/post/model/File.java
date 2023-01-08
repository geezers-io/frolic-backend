package com.modular.restfulserver.post.model;

import com.modular.restfulserver.global.common.file.application.CustomFile;
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
public class File {

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

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  // TODO: 2022-11-24 안정성 수정 필요 
  @Builder(setterPrefix = "add")
  public File(String name, Long size, Post post) {
    this.name = name;
    this.size = size;
    this.post = post;
  }

  public static File createFileByCustomFile(CustomFile file, Post post) {
    MultipartFile multipartFile = file.getFile();
    return File.builder()
      .addName(file.getCustomFilename())
      .addSize(multipartFile.getSize())
      .addPost(post)
      .build();
  }

  public String getDownloadUrl() {
    return HOST + ":" + PORT + "/api/download/" + name;
  }

}
