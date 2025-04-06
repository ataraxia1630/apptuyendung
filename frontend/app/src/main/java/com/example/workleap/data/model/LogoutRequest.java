package com.example.workleap.data.model;

public class LogoutRequest {
    private String token;

    public LogoutRequest() {}

    public LogoutRequest(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() { return token; }
}
