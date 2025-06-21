package com.example.workleap.data.model.request;

import java.util.ArrayList;

public class GroupChatRequest {
    String name;
    ArrayList<String> members;
    public GroupChatRequest() {};
    public GroupChatRequest(String name, ArrayList<String> members) {
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
