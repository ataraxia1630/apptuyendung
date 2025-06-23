package com.example.workleap.data.model.entity;

public class Follower {
    private String followId;
    private String followedId;
    private User followUser;
    private User followedUser;
    public Follower() {};
    public Follower(String followId, String followedId, User followUser, User followedUser) {
        this.followId = followId;
        this.followedId = followedId;
        this.followUser = followUser;
        this.followedUser = followedUser;
    }
    public String getFollowId() {
        return followId;
    }
    public void setFollowId(String followId) {
        this.followId = followId;
    }
    public String getFollowedId() {
        return followedId;
    }
    public void setFollowedId(String followedId) {
        this.followedId = followedId;
    }
    public User getFollowUser() {
        return followUser;
    }
    public void setFollowUser(User followUser) {
        this.followUser = followUser;
    }
    public User getFollowedUser() {
        return followedUser;
    }
    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }
}
