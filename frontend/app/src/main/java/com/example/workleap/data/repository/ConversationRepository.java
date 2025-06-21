package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.data.model.entity.JobType;
import com.example.workleap.data.model.entity.Message;
import com.example.workleap.data.model.request.ApplyAJobRequest;
import com.example.workleap.data.model.request.FriendIdRequest;
import com.example.workleap.data.model.request.GroupChatRequest;
import com.example.workleap.data.model.request.ListMemberIdRequest;
import com.example.workleap.data.model.request.UserIdRequest;
import com.example.workleap.data.model.response.ConversationResponse;
import com.example.workleap.data.model.response.JobPostResponse;
import com.example.workleap.data.model.response.ListConversationResponse;
import com.example.workleap.data.model.response.ListJobAppliedResponse;
import com.example.workleap.data.model.response.ListJobCategoryResponse;
import com.example.workleap.data.model.response.ListJobPostResponse;
import com.example.workleap.data.model.response.ListJobTypeResponse;
import com.example.workleap.data.model.response.ListMessageResponse;
import com.example.workleap.data.model.response.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ConversationRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public ConversationRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

// ======= GET =======

    public Call<ListConversationResponse> getAllChats() {
        return apiService.getAllChats();
    }

    public Call<ListConversationResponse> getAllUnreadChats() {
        return apiService.getAllUnreadChats();
    }

    public Call<ListConversationResponse> getAllGroupChats() {
        return apiService.getAllGroupChats();
    }

    public Call<ListConversationResponse> getChatById(String chatId) {
        return apiService.getChatById(chatId);
    }

    public Call<ListConversationResponse> getAllMessages(String chatId) {
        return apiService.getAllMessages(chatId);
    }

    public Call<ListMessageResponse> getMessagesByChatId(String chatId) {
        return apiService.getMessagesByChatId(chatId);
    }

    // ======= POST =======

    public Call<ConversationResponse> createChat(FriendIdRequest request) {
        return apiService.createChat(request);
    }

    public Call<ConversationResponse> createGroupChat(GroupChatRequest request) {
        return apiService.createGroupChat(request);
    }

    public Call<MessageResponse> addMemberToGroupChat(String chatId, ListMemberIdRequest members) {
        return apiService.addMemberToGroupChat(chatId, members);
    }

    public Call<Void> joinGroupChat(UserIdRequest request) {
        return apiService.joinGroupChat(request);
    }

    public Call<MessageResponse> sendMessage(Message request) {
        return apiService.sendMessage(request);
    }

    // ======= PUT =======

    public Call<MessageResponse> updateChat(String chatId, Conversation request) {
        return apiService.updateChat(chatId, request);
    }

    public Call<Void> removeMemberFromGroupChat(String chatId, UserIdRequest request) {
        return apiService.removeMemberFromGroupChat(chatId, request);
    }

    public Call<Void> leaveGroupChat(UserIdRequest request) {
        return apiService.leaveGroupChat(request);
    }

    public Call<Void> muteGroupChat(UserIdRequest request) {
        return apiService.muteGroupChat(request);
    }

    public Call<Void> unmuteGroupChat(UserIdRequest request) {
        return apiService.unmuteGroupChat(request);
    }

    public Call<MessageResponse> editMessage(String messageId, Message request) {
        return apiService.editMessage(messageId, request);
    }

    // ======= DELETE =======

    public Call<MessageResponse> deleteMessage(String messageId) {
        return apiService.deleteMessage(messageId);
    }

    public Call<MessageResponse> deleteChat(String chatId) {
        return apiService.deleteChat(chatId);
    }
}
