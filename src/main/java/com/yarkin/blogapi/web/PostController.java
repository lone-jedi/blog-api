package com.yarkin.blogapi.web;

import java.util.List;
import com.yarkin.blogapi.entity.Post;

public interface PostController {
    List<Post> getAll();

    // Returns added post
    Post add(Post newPost);

    // Updates by obtained id and received post
    void update(long postId, Post post);

    void delete(long postId);

    List<Post> findByTitle(String postTitle);

    List<Post> getAllSortedByTitle();

    List<Post> getPostWithComments();
}
