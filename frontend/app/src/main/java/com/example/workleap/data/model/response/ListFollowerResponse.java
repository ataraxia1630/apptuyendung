package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Follower;

import java.util.ArrayList;

public class ListFollowerResponse {
    String message;

    ArrayList<Follower> followers;

    public ListFollowerResponse() {
    }

    public ListFollowerResponse(String message, ArrayList<Follower> followers) {
        this.message = message;
        this.followers = followers;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Follower> getAllFollower() {
        return followers;
    }
}
