package com.example.workleap.data.model.request;

public class ReportPostRequest {
    private String reason;
    private String postId;

    public ReportPostRequest() {}

    public ReportPostRequest(String reason, String postId) {
        this.reason = reason;
        this.postId = postId;
    }
    // Getter
    public String getReason() { return reason; }
    public String getPostId() { return postId; }
}
