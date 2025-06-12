package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.Reaction;
import com.example.workleap.data.model.response.CommentResponse;
import com.example.workleap.data.model.response.ListCommentResponse;
import com.example.workleap.data.model.response.ListPostResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.response.PostResponse;
import com.example.workleap.data.model.response.ReactionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class PostRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public PostRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

    //Post
    public Call<ListPostResponse> getAllPosts() {
        return apiService.getAllPosts();
    }

    public Call<PostResponse> getPostById(String id) {
        return apiService.getPostById(id);
    }

    public Call<ListPostResponse> getPostsByCompany(String id) {
        return apiService.getPostsByCompany(id);
    }

    public Call<PostResponse> createPost(Post request) {
        return apiService.createPost(request);
    }

    public Call<PostResponse> updatePost(String id, Post request) {
        return apiService.updatePost(id, request);
    }

    public Call<MessageResponse> deletePost(String id) {
        return apiService.deletePost(id);
    }

    //Comment
    public Call<CommentResponse> createComment(Comment request) {
        return apiService.createComment(request);
    }

    public Call<ListCommentResponse> getCommentsByPost(String postId) {
        return apiService.getCommentsByPost(postId);
    }

    public Call<MessageResponse> deleteComment(String id) {
        return apiService.deleteComment(id);
    }

    //Reaction
    public Call<ReactionResponse> toggleReaction(Reaction request) {
        return apiService.toggleReaction(request);
    }

    public Call<MessageResponse> removeReaction(String postId) {
        return apiService.removeReaction(postId);
    }
}
