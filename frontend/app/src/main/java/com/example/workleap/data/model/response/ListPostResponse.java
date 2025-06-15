package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.Post;

import java.util.List;

public class ListPostResponse {
    String message;

    List<Post> posts;

    public ListPostResponse() {
    }

    public ListPostResponse(String message, List<Post> posts) {
        this.message = message;
        this.posts = posts;
    }

    public String getMessage() {
        return message;
    }

    public List<Post> getAllPost() {
        return posts;
    }
}
