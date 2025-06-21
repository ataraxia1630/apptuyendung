package com.example.workleap.data.model.request;

public class UserIdRequest {
    String userId;
    public UserIdRequest() {}
    public UserIdRequest(String userId) {
        this.userId = userId;
    }
    // Getter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
