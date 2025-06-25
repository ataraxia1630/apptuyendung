package com.example.workleap.data.model.entity;

public class Message {
    private String id;
    private String conversationId;
    private String senderId;
    private String content;
    private String sent_at;
    private String updated_at;
    private boolean isRead;
    private boolean isDeleted;
    private boolean isEdited;
    private Conversation Conversation;
    private User Sender;
    private Attachment[] attachments;

    public Message() {}

    public Message(String conversationId, String senderId, String content) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getSent_at() { return sent_at; }
    public void setSent_at(String sent_at) { this.sent_at = sent_at; }

    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public boolean isEdited() { return isEdited; }
    public void setEdited(boolean edited) { isEdited = edited; }
    public void setConversation(Conversation conversation) { Conversation = conversation; }
    public void setSender(User sender) { Sender = sender; }

    public Conversation getConversation() { return Conversation; }
    public User getSender() { return Sender; }
    public Attachment[] getAttachments() { return attachments; }
    public void setAttachments(Attachment[] attachments) { this.attachments = attachments; }
}

