package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.UserToken;

public class FCMResponse {
    String message;

    UserToken user_token;

    public FCMResponse() {
    }

    public FCMResponse(String message, UserToken user_token) {
        this.message = message;
        this.user_token = user_token;
    }

    public String getMessage() {
        return message;
    }

    public UserToken getUserToken() {
        return user_token;
    }
}
