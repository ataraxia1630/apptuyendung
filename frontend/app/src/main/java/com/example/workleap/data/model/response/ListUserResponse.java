package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.User;

import java.util.List;

public class ListUserResponse {
    List<User> users;

    public ListUserResponse() {
    }

    public ListUserResponse(String message, List<User> users) {
        this.users = users;
    }

    public List<User> getListUser() {
        return users;
    }
}
