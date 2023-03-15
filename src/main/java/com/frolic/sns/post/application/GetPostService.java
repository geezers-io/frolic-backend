package com.frolic.sns.post.application;

import com.frolic.sns.post.application.v2.GetPostBusinessManager;
import com.frolic.sns.post.dto.v2.GetPostCursorRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.repository.PostDslRespository;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPostService {

  private final PostDslRespository postDslRespository;
  private final GetPostBusinessManager getPostBusinessManager;

  public List<PostInfo> getPostsOwnedByUser(User user, GetPostCursorRequest getPostCursorRequest) {
    List<Post> posts = postDslRespository.findPostByUser(getPostCursorRequest.getCursorId(), user.getId());
    return getPostBusinessManager.createPostInfos(posts);
  }

  public List<PostInfo> getPostsByHashtags(List<String> hashtags, GetPostCursorRequest getPostCursorRequest) {
    List<Post> posts = postDslRespository.findPostByHashtags(getPostCursorRequest.getCursorId(), hashtags);
    return getPostBusinessManager.createPostInfos(posts);
  }

}
