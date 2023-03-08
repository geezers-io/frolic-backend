package com.frolic.sns.global.common.file.model;

import com.frolic.sns.post.model.Post;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.frolic.sns.global.util.message.CommonMessageUtils.*;

@Entity(name = "files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplicationFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  private Long size;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @Builder(setterPrefix = "add")
  public ApplicationFile(String name, Long size) {
    Assert.hasText(name, getIllegalFieldError("name"));
    Assert.isInstanceOf(Long.class, size, getIllegalFieldError("size"));
    this.name = name;
    this.size = size;
  }

}
