package com.yarkin.blogapi.web.post;

import com.yarkin.blogapi.entity.Post;
import com.yarkin.blogapi.web.PostController;
import com.yarkin.blogapi.web.PostRateController;

import java.util.List;

public class DefaultPostController implements PostController, PostRateController {

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post add(Post newPost) {
        return null;
    }

    @Override
    public void update(long postId, Post post) {

    }

    @Override
    public void delete(long postId) {

    }

    @Override
    public List<Post> findByTitle(String postTitle) {
        return null;
    }

    @Override
    public List<Post> getAllSortedByTitle() {
        return null;
    }

    @Override
    public List<Post> getPostWithComments() {
        return null;
    }

    @Override
    public List<Post> getTopPosts() {
        return null;
    }

    @Override
    public void markAsTop(long postId) {

    }

    @Override
    public void removeFromTop(long postId) {

    }
}
