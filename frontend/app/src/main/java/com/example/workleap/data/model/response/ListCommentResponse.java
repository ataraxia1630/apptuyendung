package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Post;

import java.util.List;

public class ListCommentResponse {
    String message;

    List<Comment> comments;

    public ListCommentResponse() {
    }

    public ListCommentResponse(String message, List<Comment> comments) {
        this.message = message;
        this.comments = comments;
    }

    public String getMessage() {
        return message;
    }

    public List<Comment> getAllComment() {
        return comments;
    }
}
