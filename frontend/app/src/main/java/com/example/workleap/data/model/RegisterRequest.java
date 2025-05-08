package com.example.workleap.data.model;

public class RegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String phoneNumber;
    private String role;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, String confirmPassword, String email, String phoneNumber, String role) {
        this.username = username;
        this.password = password;
        this.password = confirmPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getter (Retrofit cần getter để serialize thành JSON)
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }
}
