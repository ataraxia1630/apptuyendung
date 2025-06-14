package com.example.workleap.data.model.entity;

public class PostContent {
    private String id;
    private String postId;
    private String type; // Assuming ContentType is an enum, represented as String for simplicity
    private String value;
    private int order;

    // Default constructor
    public PostContent() {
    }

    // Full constructor
    public PostContent(String id, String postId, String type, String value, int order) {
        this.id = id;
        this.postId = postId;
        this.type = type;
        this.value = value;
        this.order = order;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}