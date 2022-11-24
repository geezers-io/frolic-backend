package com.modular.restfulserver.article.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @Column(nullable = false)
  private String dir;

  @Column(unique = true)
  private Long size;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

}
