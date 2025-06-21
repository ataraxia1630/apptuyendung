package com.example.workleap.data.model.request;

import com.example.workleap.data.model.entity.Conversation;

import java.util.ArrayList;

public class ConversationRequest {
    String name;
    Conversation ;
    public ConversationRequest() {};
    public ConversationRequest(String name, ArrayList<String> members) {
        this.name = name;
        this.members = members;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<String> getMembers() {
        return members;
    }
    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
}
