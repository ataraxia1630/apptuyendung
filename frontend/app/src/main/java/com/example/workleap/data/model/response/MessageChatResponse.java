package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Message;

import java.util.ArrayList;

public class MessageChatResponse {

    Message mess;

    public MessageChatResponse() {
    }

    public MessageChatResponse(Message mess) {
        this.mess = mess;
    }

    public Message getMessage() {
        return mess;
    }
}
