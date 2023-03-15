package com.frolic.sns.post.application.v2;

import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.repository.*;
import com.frolic.sns.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetPostBusinessManager {

  private final LikeRepository likeRepository;
  private final CommentDslRepository commentDslRepository;

  public List<PostInfo> createPostInfos(List<Post> posts) {
    return posts.stream().map(
      post -> PostInfo.addProperties(post)
        .addCommentCount(
          commentDslRepository.getCommentCount(post.getId())
        )
        .addLikeCount(likeRepository.countAllByPost(post))
        .addIsLikeUp(likeRepository.existsByPostAndUser(post, post.getUser()))
        .addUserInfo(UserInfo.from(post.getUser()))
        .build()
    )
      .collect(Collectors.toList());
  }

}
