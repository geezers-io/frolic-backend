package com.modular.restfulserver.article.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "hashtags")
public class Hashtag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

  @Builder(setterPrefix = "add")
  public Hashtag(String name) {
    Assert.hasText(name, "name field must be string");
    this.name = name;
  }

}
