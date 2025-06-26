package com.example.workleap.data.repository;

import android.content.Context;

import com.example.workleap.data.api.ApiService;
import com.example.workleap.data.api.RetrofitClient;
import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.Message;
import com.example.workleap.data.model.request.FriendIdRequest;
import com.example.workleap.data.model.request.GroupChatRequest;
import com.example.workleap.data.model.request.ListMemberIdRequest;
import com.example.workleap.data.model.request.UserIdRequest;
import com.example.workleap.data.model.response.ConversationResponse;
import com.example.workleap.data.model.response.ListConversationUserResponse;
import com.example.workleap.data.model.response.ListMessageResponse;
import com.example.workleap.data.model.response.ListNotificationResponse;
import com.example.workleap.data.model.response.MessageChatResponse;
import com.example.workleap.data.model.response.MessageResponse;

import retrofit2.Call;

public class ConversationRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public ConversationRepository(Context context) {
        preferencesManager = new PreferencesManager(context);
        String token = preferencesManager.getToken();
        apiService = RetrofitClient.getClient(token).create(ApiService.class);
    }

// ======= GET =======

    public Call<ListConversationUserResponse> getAllChats() {
        return apiService.getAllChats();
    }

    public Call<ListConversationUserResponse> getAllUnreadChats() {
        return apiService.getAllUnreadChats();
    }

    public Call<ListConversationUserResponse> getAllGroupChats() {
        return apiService.getAllGroupChats();
    }

    public Call<ConversationResponse> getChatById(String chatId) {
        return apiService.getChatById(chatId);
    }

    public Call<ListConversationUserResponse> getAllMessages(String chatId) {
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

    public Call<MessageChatResponse> sendMessage(Message request) {
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

    //notification
    public Call<ListNotificationResponse> getAllNotification(){
        return apiService.getAllNotification();
    }
    public Call<MessageResponse> deleteNotification(String notificationId)
    {
        return  apiService.deleteNotification(notificationId);
    }
}
