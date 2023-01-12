package com.frolic.sns.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "follows")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Follow {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "follower_id")
  private User followerId;


  @ManyToOne
  @JoinColumn(name = "following_id")
  private User followingId;

  public void setFollowerId(User followerId) {
    this.followerId = followerId;
  }

  public void setFollowingId(User followingId) {
    this.followingId = followingId;
  }

}
