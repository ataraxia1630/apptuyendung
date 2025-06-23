package com.example.workleap.data.model.entity;

import java.util.ArrayList;

public class Conversation {
    private String id;
    private String name;
    private boolean isGroup;
    private String created_at;
    private String updated_at;
    private Message[] Message;
    private ArrayList<ConversationUser> members;
    private ConversationLabel[] labels;
    public Conversation() {}

    public Conversation(String name, boolean isGroup, String created_at, String updated_at) {
        this.name = name;
        this.isGroup = isGroup;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isGroup() { return isGroup; }
    public void setGroup(boolean group) { isGroup = group; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }

    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }
    public Message[] getMessage() { return Message; }
    public void setMessage(Message[] message) { Message = message; }
    public ArrayList<ConversationUser> getMembers() { return members; }
    public void setMembers(ArrayList<ConversationUser> members) { this.members = members; }

    public ConversationLabel[] getLabels() { return labels; }
    public void setLabels(ConversationLabel[] labels) { this.labels = labels; }
}

