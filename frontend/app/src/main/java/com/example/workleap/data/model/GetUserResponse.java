package com.example.workleap.data.model;

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
