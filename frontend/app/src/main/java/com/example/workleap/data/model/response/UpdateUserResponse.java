package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.User;

public class UpdateUserResponse {
    private String message;
    private User user;

    public UpdateUserResponse() {}
    public UpdateUserResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() { return message; }
    public User getUser() { return user; }
}
