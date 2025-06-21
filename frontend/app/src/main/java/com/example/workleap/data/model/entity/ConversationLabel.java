package com.example.workleap.data.model.entity;

public class ConversationLabel {
    private String id;
    private String conversationId;
    private String label;
    private String created_at;
    private Conversation Conversation;

    public ConversationLabel() {}

    public ConversationLabel(String conversationId, String label, String created_at) {
        this.conversationId = conversationId;
        this.label = label;
        this.created_at = created_at;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
    public void setConversation(Conversation conversation) { Conversation = conversation; }
    public Conversation getConversation() { return Conversation; }
}

