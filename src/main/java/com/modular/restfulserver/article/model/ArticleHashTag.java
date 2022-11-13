package com.modular.restfulserver.article.model;

import javax.persistence.*;

@Entity(name = "article_hashtags")
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

}
