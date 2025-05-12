package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.User;

public class GetUserResponse {
    private String message;

    private User user;

    public GetUserResponse() {}

    public GetUserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() { return user; }
}
