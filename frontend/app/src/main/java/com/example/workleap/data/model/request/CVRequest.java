package com.example.workleap.data.model.request;

import java.io.File;

public class CVRequest {

    private File file;
    private String title;

    public CVRequest() {}

    public CVRequest(File file, String title) {
        this.file = file;
        this.title = title;
    }

    // Getter
    public File getFile() { return file; }
    public String getTitle() { return title; }
}
