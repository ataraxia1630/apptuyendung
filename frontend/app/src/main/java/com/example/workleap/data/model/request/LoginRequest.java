package com.example.workleap.data.model.request;

public class LoginRequest {
    private String username;
    private String email;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getter
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
