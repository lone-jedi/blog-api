package com.yarkin.blogapi.web;

import com.yarkin.blogapi.entity.Post;

import java.util.List;

public interface PostRateController {
    List<Post> getTopPosts();

    void markAsTop(long postId);

    void removeFromTop(long postId);
}
