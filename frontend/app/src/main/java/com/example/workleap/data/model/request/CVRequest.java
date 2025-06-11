package com.example.workleap.data.model.request;

import com.example.workleap.data.model.entity.JobApplied;

import java.io.File;
import java.util.Date;
import java.util.List;

public class CVRequest {
    private String title;

    public CVRequest() {}

    public CVRequest(String title) {
        this.title = title;
    }

    // Getter
    public String getTitle() { return title; }
}
