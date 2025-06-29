package com.example.workleap.data.model.entity;

import java.util.Date;

public class Notification {
    private String id;
    private String title;
    private String userId;
    private String message;
    private String status;
    private Date created_at;
    private User user;

    // Constructors
    public Notification() {}

    public Notification(String id, String title, String userId, String message, String status, Date createdAt) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.message = message;
        this.status = status;
        this.created_at = createdAt;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreated_at() { return created_at; }
    public void setCreated_at(Date created_at) { this.created_at = created_at; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
