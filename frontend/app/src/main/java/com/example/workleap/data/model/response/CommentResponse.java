package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Post;

public class CommentResponse {
    String message;

    Comment comment;

    public CommentResponse() {
    }

    public CommentResponse(String message, Comment comment) {
        this.message = message;
        this.comment = comment;
    }

    public String getMessage() {
        return message;
    }

    public Comment getComment() {
        return comment;
    }
}
