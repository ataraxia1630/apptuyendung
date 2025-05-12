package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.User;

public class LoginResponse {
    private String message;
    private String token;
    private User user;

    public LoginResponse() {}

    public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {return user;}
}
