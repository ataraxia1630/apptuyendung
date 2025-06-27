package com.example.workleap.data.model.request;

public class FCMRequest {
    private String userId;
    private String fcm_token;

    public FCMRequest() {}

    public FCMRequest(String userId, String fcm_token)
    {
        this.userId = userId;
        this.fcm_token = fcm_token;
    }

    // Getter
    public String getUserId() { return userId; }
    public String getFcm_token() { return fcm_token; }
}
