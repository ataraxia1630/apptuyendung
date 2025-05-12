package com.example.workleap.data.model.request;

public class UpdateUserRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String avatar; // Có thể null
    private String background; // Có thể null

    public UpdateUserRequest() {};
    public UpdateUserRequest(String username, String password, String email, String phoneNumber, String avatar, String background)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.background = background;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAvatar() { return avatar; }
    public String getBackground() { return background; }
    public String getPhoneNumber() { return phoneNumber; }
}
