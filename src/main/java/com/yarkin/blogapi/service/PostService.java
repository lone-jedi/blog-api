package com.yarkin.blogapi.service;

import com.yarkin.blogapi.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAll();

    Post getById(long postId);

    // Returns added post
    Post add(Post newPost);

    // Updates by obtained id and received post
    Post update(long postId, Post post);

    Post delete(long postId);

    List<Post> findByTitle(String postTitle);

    List<Post> getAllSortedByTitle();

    List<Post> getAllTopPosts();

    Post markAsTop(long postId);

    Post removeFromTop(long postId);
}
