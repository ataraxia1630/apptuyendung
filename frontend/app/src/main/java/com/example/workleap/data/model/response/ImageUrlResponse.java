package com.example.workleap.data.model.response;

public class ImageUrlResponse {
    private String message;
    private String url;
    public ImageUrlResponse() {}

    public ImageUrlResponse(String message, String url) {
        this.message = message; this.url = url;
    }

    public String getMessage() {
        return message;
    }
    public String getUrl() {return url; }
}
