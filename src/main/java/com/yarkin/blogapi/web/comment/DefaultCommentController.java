package com.yarkin.blogapi.web.comment;

import com.yarkin.blogapi.entity.Comment;
import com.yarkin.blogapi.web.CommentController;

import java.util.List;

public class DefaultCommentController implements CommentController {

    @Override
    public Comment add(long postId, Comment newComment) {
        return null;
    }

    @Override
    public List<Comment> getAllByPost(long postId) {
        return null;
    }

    @Override
    public Comment getByPost(long postId, long commentId) {
        return null;
    }
}
