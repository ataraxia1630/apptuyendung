package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Experience;
import com.example.workleap.data.model.entity.Field;

import java.util.List;

public class ListExperienceResponse {
    String message;

    List<Experience> experiences;

    public ListExperienceResponse() {
    }

    public ListExperienceResponse(String message, List<Experience> experiences) {
        this.message = message;
        this.experiences = experiences;
    }

    public String getMessage() {
        return message;
    }

    public List<Experience> getAllExperience() {
        return experiences;
    }
}
