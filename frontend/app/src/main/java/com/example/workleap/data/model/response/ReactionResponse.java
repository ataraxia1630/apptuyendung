package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Reaction;

public class ReactionResponse {
    String message;

    Reaction reaction;

    public ReactionResponse() {
    }

    public ReactionResponse(String message, Reaction reaction) {
        this.message = message;
        this.reaction = reaction;
    }

    public String getMessage() {
        return message;
    }

    public Reaction getReaction() {
        return reaction;
    }
}
