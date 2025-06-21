package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.Message;

import java.util.List;

public class ListMessageResponse {
    String message;

    List<Message> messages;

    public ListMessageResponse() {
    }

    public ListMessageResponse(String message, List<Message> messages) {
        this.message = message;
        this.messages = messages;
    }

    public String getMessage() {
        return message;
    }

    public List<Message> getAllConversation() {
        return messages;
    }
}
