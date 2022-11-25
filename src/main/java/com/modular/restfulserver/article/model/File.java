package com.modular.restfulserver.article.model;

import static com.modular.restfulserver.global.config.spring.ApplicationConstant.*;

import com.modular.restfulserver.global.common.file.application.CustomFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

  // TODO: 2022-11-24 안정성 수정 필요 
  @Builder(setterPrefix = "add")
  public File(String name, Long size, Article article) {
    this.name = name;
    this.size = size;
    this.article = article;
  }

  public static File createFileByCustomFile(CustomFile file, Article article) {
    MultipartFile multipartFile = file.getFile();
    return File.builder()
      .addName(file.getCustomFilename())
      .addSize(multipartFile.getSize())
      .addArticle(article)
      .build();
  }

  public String getDownloadUrl() {
    return HOST + ":" +PORT + "/download/" + name;
  }

}
