package com.example.workleap.data.model.entity;

import java.io.Serializable;

public class ConversationUser implements Serializable {
    private String conversationId;
    private String userId;
    private boolean isAdmin;
    private boolean allowNotification;
    private String joined_at;
    private String left_at;
    private String deleted_at;
    private String updated_at;
    private Conversation Conversation;
    private User User;

    public ConversationUser() {}

    public ConversationUser(String conversationId, String userId, boolean isAdmin,
                            boolean allowNotification, String joined_at, String left_at,
                            String deleted_at, String updated_at) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.isAdmin = isAdmin;
        this.allowNotification = allowNotification;
        this.joined_at = joined_at;
        this.left_at = left_at;
        this.deleted_at = deleted_at;
        this.updated_at = updated_at;
    }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    public boolean isAllowNotification() { return allowNotification; }
    public void setAllowNotification(boolean allowNotification) { this.allowNotification = allowNotification; }

    public String getJoined_at() { return joined_at; }
    public void setJoined_at(String joined_at) { this.joined_at = joined_at; }

    public String getLeft_at() { return left_at; }
    public void setLeft_at(String left_at) { this.left_at = left_at; }

    public String getDeleted_at() { return deleted_at; }
    public void setDeleted_at(String deleted_at) { this.deleted_at = deleted_at; }

    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }
    public void setConversation(Conversation conversation) { Conversation = conversation; }
    public void setUser(User user) { User = user; }
    public Conversation getConversation() { return Conversation; }
    public User getUser() { return User; }
}

