package com.example.workleap.data.model.entity;

import java.util.Date;

public class Comment {
    private String id;
    private String userId;
    private String postId;
    private String commentId;
    private String commentDetail;
    private Date created_at;
    private Date updated_at;

    // Default constructor
    public Comment() {
    }

    // Full constructor
    public Comment(String id, String userId, String postId, String commentId, String commentDetail, Date createdAt, Date updated_at) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.commentDetail = commentDetail;
        this.created_at = createdAt;
        this.updated_at = updated_at;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date createdAt) {
        this.created_at = createdAt;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }
}