package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class ListMessageResponse {

    ArrayList<Message> messages;

    public ListMessageResponse() {
    }

    public ListMessageResponse(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Message> getAllMessage() {
        return messages;
    }
}
