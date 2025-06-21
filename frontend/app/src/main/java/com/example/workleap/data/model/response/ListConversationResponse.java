package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.ConversationUser;

import java.util.ArrayList;
import java.util.List;

public class ListConversationResponse {
    String message;

    ArrayList<Conversation> chats;

    public ListConversationResponse() {
    }

    public ListConversationResponse(String message, ArrayList<Conversation> chats) {
        this.message = message;
        this.chats = chats;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Conversation> getAllConversation() {
        return chats;
    }
}
