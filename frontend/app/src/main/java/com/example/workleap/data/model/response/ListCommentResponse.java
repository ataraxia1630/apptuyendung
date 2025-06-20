package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class ListCommentResponse {
    String message;

    ArrayList<Comment> comments;

    public ListCommentResponse() {
    }

    public ListCommentResponse(String message, ArrayList<Comment> comments) {
        this.message = message;
        this.comments = comments;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Comment> getAllComment() {
        return comments;
    }
}
