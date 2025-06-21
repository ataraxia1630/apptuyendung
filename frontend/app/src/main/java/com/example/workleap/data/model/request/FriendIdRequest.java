package com.example.workleap.data.model.request;

import java.util.List;

public class FriendIdRequest {
    String friendId;

    public FriendIdRequest() {}

    public FriendIdRequest(String fieldIds) {
        this.friendId = fieldIds;
    }

    // Getter
    public String getFieldIds() { return friendId; }
    public void setFieldIds(String fieldIds) { this.friendId = fieldIds; }
}
