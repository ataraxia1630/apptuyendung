package com.example.workleap.data.model.entity;


import java.util.Date;

public class Post {
    private String id;
    private String companyId;
    private String title;
    private Company Company;
    private PostContent[] contents;
    private Reaction[] Reaction;
    private Comment[] Comment;
    private Date created_at;
    private Date updated_at;

    // Default constructor
    public Post() {
    }

    // Full constructor
    public Post(String id, String companyId, String title, Date created_at, Date updated_at) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date createdAt) {
        this.created_at = createdAt;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Company getCompany() {
        return Company;
    }

    public PostContent[] getContents() {
        return contents;
    }

    public Reaction[] getReaction() {
        return Reaction;
    }

    public Comment[] getComment() {
        return Comment;
    }

}