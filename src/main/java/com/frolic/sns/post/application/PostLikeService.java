package com.frolic.sns.post.application;

import com.frolic.sns.post.dto.LikeCount;
import com.frolic.sns.post.exception.AlreadyExistsLikeException;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.Like;
import com.frolic.sns.post.repository.LikeDslRepository;
import com.frolic.sns.post.repository.PostRepository;
import com.frolic.sns.post.repository.LikeRepository;
import com.frolic.sns.global.exception.NotFoundResourceException;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {

  private final LikeRepository likeRepository;
  private final LikeDslRepository likeDslRepository;
  private final PostRepository postRepository;

  public LikeCount addLike(User user, Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    if (likeDslRepository.isExistsLike(user, post))
      throw new AlreadyExistsLikeException();

    Like newLike = new Like(user, post);
    likeRepository.save(newLike);

    Long count = likeDslRepository.countAllLike(post);
    return new LikeCount(count);
  }

  public LikeCount removeLike(User user, Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    Like like = likeDslRepository.findLike(user, post).orElseThrow(NotFoundResourceException::new);
    likeRepository.delete(like);
    Long count = likeDslRepository.countAllLike(post);
    return new LikeCount(count);
  }

}
