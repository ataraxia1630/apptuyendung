package com.example.workleap.data.model;

public class RegisterResponse {
    private String message;

    private User user;

    public RegisterResponse() {}

    public RegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
