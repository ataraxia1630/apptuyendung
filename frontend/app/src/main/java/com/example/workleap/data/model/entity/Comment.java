package com.example.workleap.data.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Comment implements Serializable {
    private String id;
    private String userId;
    private String postId;
    private String CommentId;
    private String CommentDetail;
    private Date created_at;
    private Date updated_at;
    private User User;
    private Post Post;
    private Comment parentComment;
    private ArrayList<Comment> childComment;


    // Default constructor
    public Comment() {
    }

    // Full constructor
    public Comment(String userId, String postId, String commentDetail) {
        this.userId = userId;
        this.postId = postId;
        this.CommentDetail = commentDetail;
    }

    //Child Comment
    public Comment(String userId, String postId, String commentId, String commentDetail) {
        this.userId = userId;
        this.postId = postId;
        this.CommentId = commentId;
        this.CommentDetail = commentDetail;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String commentId) {
        this.CommentId = commentId;
    }

    public String getCommentDetail() {
        return CommentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.CommentDetail = commentDetail;
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
    public Comment getParentComment() {
        return parentComment;
    }
    public ArrayList<Comment> getChildComment() {
        return childComment;
    }
    public User getUser() {
        return User;
    }
    public Post getPost() {
        return Post;
    }
    public void setUser(User user) {
        this.User = user;
    }
    public void setPost(Post post) {
        this.Post = post;
    }
}