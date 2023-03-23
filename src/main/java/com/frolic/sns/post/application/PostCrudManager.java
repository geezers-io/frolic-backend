package com.frolic.sns.post.application;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.global.exception.NotFoundResourceException;
import com.frolic.sns.post.dto.v2.CreatePostRequest;
import com.frolic.sns.post.dto.GetPostCursorRequest;
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
public class PostCrudManager {

  private final HashTagManager hashTagManager;
  private final PostRepository postRepository;
  private final PostDslRespository postDslRespository;
  private final PostFileDslRepository postFileDslRepository;
  private final GetPostBusinessManager getPostBusinessManager;
  private final CreatePostBusinessManager createPostBusinessManager;
  private final UpdatePostBusinessManager updatePostBusinessManager;


  public List<PostInfo> getPosts(GetPostCursorRequest postCursorRequest) {
    Long cursorId = postCursorRequest.getCursorId();
    List<Post> posts = postDslRespository.findPosts(cursorId);
    return getPostBusinessManager.createPostInfos(posts);
  }

  public PostInfo createPost(User user, CreatePostRequest createPostRequest) {
    List<String> hashtags = createPostRequest.getHashtags();
    Post newPost = createPostBusinessManager.commitAndReturnPost(createPostRequest, user);
    hashTagManager.connectHashtagsWithPost(hashtags, newPost);

    List<PostFile> createdPostFiles = postFileDslRepository.createPostFilesByIds(newPost, createPostRequest.getImageIds());
    List<FileInfo> fileInfos = createPostBusinessManager.getFileInfosFromPostFiles(createdPostFiles);

    return createPostBusinessManager.getPostInfo(newPost, createPostRequest, user, fileInfos);
  }

  public PostInfo updatePost(Long postId, User user, UpdatePostRequest updatePostRequest) {
    Post post = postRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    updatePostBusinessManager.checkUserPermission(user, post.getUser());
    updatePostBusinessManager.updateHashtags(post, updatePostRequest.getHashtags());
    List<FileInfo> fileInfos = updatePostBusinessManager.updateImages(post, updatePostRequest.getImageIds());
    post.updateTextContent(updatePostRequest.getTextContent());
    postRepository.save(post);

    return updatePostBusinessManager.getPostInfo(post, user, updatePostRequest.getHashtags(), fileInfos);
  }

  public void deletePost(Long postId, Long userId) {
    Post post = postRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    boolean isOwner = post.getUser().getId().equals(userId);
    if (!isOwner) throw new NotPermissionException();
    postRepository.delete(post);
  }

}
