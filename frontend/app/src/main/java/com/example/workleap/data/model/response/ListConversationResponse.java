package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Conversation;

import java.util.List;

public class ListConversationResponse {
    String message;

    List<Conversation> chats;

    public ListConversationResponse() {
    }

    public ListConversationResponse(String message, List<Conversation> chats) {
        this.message = message;
        this.chats = chats;
    }

    public String getMessage() {
        return message;
    }

    public List<Conversation> getAllConversation() {
        return chats;
    }
}
