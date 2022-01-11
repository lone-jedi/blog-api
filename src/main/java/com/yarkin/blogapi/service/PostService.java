package com.yarkin.blogapi.service;

import com.yarkin.blogapi.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAll();

    // Returns added post
    Post add(Post newPost);

    // Updates by obtained id and received post
    void update(long postId, Post post);

    void delete(long postId);
}
