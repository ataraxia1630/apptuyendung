package com.example.workleap.data.model.entity;

import java.util.Date;

public class UserStatus {
    private String userId;
    private String status;   //ONLINE, AWAY, OFFLINE
    private Date lastActive_at;
    private User User;
    public UserStatus() {}
    public UserStatus(String userId, String status, Date lastActive_at) {
        this.userId = userId;
        this.status = status;
        this.lastActive_at = lastActive_at;
    }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getLastActive_at() { return lastActive_at; }
    public void setLastActive_at(Date lastActive_at) { this.lastActive_at = lastActive_at; }

    public void setUser(User user) { User = user; }
    public User getUser() { return User; }
}
