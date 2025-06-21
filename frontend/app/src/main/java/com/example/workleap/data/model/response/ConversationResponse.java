package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Conversation;

import java.util.List;

public class ConversationResponse {
    String message;

    Conversation chat;

    public ConversationResponse() {
    }

    public ConversationResponse(String message, Conversation chat) {
        this.message = message;
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public Conversation getConversation() {
        return chat;
    }
}
