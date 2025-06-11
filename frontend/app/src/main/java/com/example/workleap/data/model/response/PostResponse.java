package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Post;

import java.util.List;

public class PostResponse {
    String message;

    Post post;

    public PostResponse() {
    }

    public PostResponse(String message, Post post) {
        this.message = message;
        this.post = post;
    }

    public String getMessage() {
        return message;
    }

    public Post getPost() {
        return post;
    }
}
