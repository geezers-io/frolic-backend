package com.frolic.sns.post.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "hashtags")
public class Hashtag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "hashtag")
  private final List<PostHashTag> postHashTags = new ArrayList<>();

  @Builder(setterPrefix = "add")
  public Hashtag(String name) {
    Assert.hasText(name, "name field must be string");
    this.name = name;
  }

}
