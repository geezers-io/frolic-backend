package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.exception.AlreadyExistsLikeException;
import com.modular.restfulserver.post.model.Post;
import com.modular.restfulserver.post.model.Like;
import com.modular.restfulserver.post.repository.PostRepository;
import com.modular.restfulserver.post.repository.LikeRepository;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.global.exception.NotFoundResourceException;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeManagerImpl implements PostLikeManager {

  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final JwtProvider jwtProvider;

  @Override
  public Long likePostByTokenUser(String token, Long postId) {

    User user = getUserByToken(token);
    Post post = postRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    if (likeRepository.existsByPostAndUser(post, user))
      throw new AlreadyExistsLikeException();

    Like newLike = new Like(user, post);
    likeRepository.save(newLike);

    return likeRepository.countAllByPost(post);
  }

  @Override
  public Long unLikePostByTokenUser(String token, Long postId) {
    User user = getUserByToken(token);
    Post post = postRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    Like like = likeRepository.findByPostAndUser(post, user).orElseThrow(NotFoundResourceException::new);

    likeRepository.delete(like);
    return likeRepository.countAllByPost(post);
  }

  private User getUserByToken(String token) {
    return userRepository.findByEmail(jwtProvider.getUserEmailByToken(token)).orElseThrow(UserNotFoundException::new);
  }

}
