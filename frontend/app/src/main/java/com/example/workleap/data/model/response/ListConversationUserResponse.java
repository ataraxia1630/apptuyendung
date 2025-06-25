package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.ConversationUser;

import java.util.ArrayList;

public class ListConversationUserResponse {
    String message;

    ArrayList<ConversationUser> chats;

    public ListConversationUserResponse() {
    }

    public ListConversationUserResponse(String message, ArrayList<ConversationUser> chats) {
        this.message = message;
        this.chats = chats;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ConversationUser> getAllConversationUser() {
        return chats;
    }
}
