package com.frolic.sns.post.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "post_hashtags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHashTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne
  @JoinColumn(name = "tag_id")
  private Hashtag hashtag;

  @Builder(setterPrefix = "add")
  public PostHashTag(Post post, Hashtag hashtag) {
    this.post = post;
    this.hashtag = hashtag;
  }

}
