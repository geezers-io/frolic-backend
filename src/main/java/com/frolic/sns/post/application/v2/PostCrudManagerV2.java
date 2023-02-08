package com.frolic.sns.post.application.v2;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.common.jwt.JwtEntityLoader;
import com.frolic.sns.global.exception.NotFoundResourceException;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.dto.v2.UpdatePostRequest;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostFile;
import com.frolic.sns.post.repository.*;
import com.frolic.sns.user.exception.NotPermissionException;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostCrudManagerV2 {

  private final HashTagManager hashTagManager;
  private final PostRepository postRepository;
  private final PostFileDslRepository postFileDslRepository;
  private final JwtEntityLoader entityLoader;
  private final CreatePostBusinessManager createPostBusinessManager;
  private final UpdatePostBusinessManager updatePostBusinessManager;

  public PostInfo createPost(String token, CreatePostRequest createPostRequest) {
    User user = entityLoader.getUser(token);
    List<String> hashtags = createPostRequest.getHashtags();
    Post newPost = createPostBusinessManager.commitAndReturnPost(createPostRequest, user);
    hashTagManager.connectHashtagsWithPost(hashtags, newPost);

    List<PostFile> createdPostFiles = postFileDslRepository.createPostFilesByIds(newPost, createPostRequest.getImageIds());
    List<FileInfo> fileInfos = createPostBusinessManager.getFileInfosFromPostFiles(createdPostFiles);

    return createPostBusinessManager.getPostInfo(newPost, createPostRequest, user, fileInfos);
  }

  public PostInfo updatePost(Long postId, String token, UpdatePostRequest updatePostRequest) {
    User user = entityLoader.getUser(token);
    Post post = postRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    updatePostBusinessManager.checkUserPermission(user, post.getUser());
    updatePostBusinessManager.updateHashtags(post, updatePostRequest.getHashtags());
    List<FileInfo> fileInfos = updatePostBusinessManager.updateImages(post, updatePostRequest.getImageIds());
    post.updateTextContent(updatePostRequest.getTextContent());
    postRepository.save(post);

    return updatePostBusinessManager.getPostInfo(post, user, updatePostRequest.getHashtags(), fileInfos);
  }

  public void deletePost(Long postId, String token) {
    User user = entityLoader.getUser(token);
    Post post = postRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    boolean isOwner = post.getUser().getId().equals(user.getId());
    if (!isOwner)
      throw new NotPermissionException();
    postRepository.delete(post);
  }

}
