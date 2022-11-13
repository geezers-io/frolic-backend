package com.modular.restfulserver.article.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "article_hashtags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleHashTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

  @ManyToOne
  @JoinColumn(name = "tag_id")
  private Hashtag hashtag;

  @Builder(setterPrefix = "add")
  public ArticleHashTag(Article article, Hashtag hashtag) {
    this.article = article;
    this.hashtag = hashtag;
  }

}
