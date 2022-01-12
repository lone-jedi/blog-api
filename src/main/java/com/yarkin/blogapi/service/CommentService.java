package com.yarkin.blogapi.service;

import com.yarkin.blogapi.entity.Comment;
import com.yarkin.blogapi.entity.Post;

import java.util.List;

public interface CommentService {

    List<Comment> getAllByPostId(long postId);

    Comment getByPostId(long postId, long commentId);

    // Returns added comment
    Comment add(long postId, Comment newComment);

}
