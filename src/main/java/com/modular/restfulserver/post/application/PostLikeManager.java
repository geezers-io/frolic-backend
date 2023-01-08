package com.modular.restfulserver.post.application;

public interface PostLikeManager {

  Long likePostByTokenUser(String token, Long postId);

  Long unLikePostByTokenUser(String token, Long postId);

}
