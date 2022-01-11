package com.yarkin.blogapi.web;

import java.util.List;
import com.yarkin.blogapi.entity.Comment;

public interface CommentController {
    Comment add(long postId, Comment newComment);

    List<Comment> getAllByPost(long postId);

    Comment getByPost(long postId, long commentId);
}
