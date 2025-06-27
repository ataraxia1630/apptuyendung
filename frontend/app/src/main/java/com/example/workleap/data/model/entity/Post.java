package com.example.workleap.data.model.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Post implements Serializable {
    private String id;
    private String companyId;
    private String title;
    private Company Company;
    private ArrayList<PostContent> contents;
    private ArrayList<Reaction> Reaction;
    private ArrayList<Comment> Comment;
    private Date created_at;
    private Date updated_at;


    // Default constructor
    public Post() {
    }

    // Full constructor
    public Post(String companyId, String title, ArrayList<PostContent> contents) {
        this.companyId = companyId;
        this.title = title;
        this.contents = contents;
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

    public ArrayList<PostContent> getContents() {
        return contents;
    }

    public ArrayList<Reaction> getReaction() {
        return Reaction;
    }

    public ArrayList<Comment> getComment() {
        return Comment;
    }

}