package com.example.workleap.data.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Reaction implements Serializable {
    private String postId;
    private String userId;
    private String reactionType; // Assuming ReactionType is an enum, represented as String for simplicity
    private Date created_at;
    private Date updated_at;
    private String postOwnerId;
    private boolean removed;

    // Default constructor
    public Reaction() {
    }

    // Full constructor
    public Reaction(String postId, String userId, String reactionType) {
        this.postId = postId;
        this.userId = userId;
        this.reactionType = reactionType;
    }

    // Getters and Setters
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }
    public void getRemoved(boolean removed) {
        this.removed = removed;
    }
    public boolean isRemoved() {
        return removed;
    }
}