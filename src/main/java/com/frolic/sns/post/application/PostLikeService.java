package com.frolic.sns.post.application;

import com.frolic.sns.post.exception.AlreadyExistsLikeException;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.Like;
import com.frolic.sns.post.repository.PostRepository;
import com.frolic.sns.post.repository.LikeRepository;
import com.frolic.sns.global.config.security.JwtProvider;
import com.frolic.sns.global.exception.NotFoundResourceException;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
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
