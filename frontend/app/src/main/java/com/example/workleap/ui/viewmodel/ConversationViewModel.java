package com.example.workleap.ui.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.request.FriendIdRequest;
import com.example.workleap.data.model.request.GroupChatRequest;
import com.example.workleap.data.model.request.ListMemberIdRequest;
import com.example.workleap.data.model.request.UserIdRequest;
import com.example.workleap.data.model.response.ConversationResponse;
import com.example.workleap.data.model.response.ListConversationResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.repository.ConversationRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationViewModel extends ViewModel {
    private ConversationRepository conversationRepository;
    private final MutableLiveData<List<Conversation>> allChats = new MutableLiveData<>();
    private final MutableLiveData<Conversation> singleChat = new MutableLiveData<>();
    private final MutableLiveData<String> messageResult = new MutableLiveData<>();
    private final MutableLiveData<String> errorResult = new MutableLiveData<>();

    public void initiateRepository(Context context) {
        this.conversationRepository = new ConversationRepository(context);
    }

    // ===== Getter =====
    public LiveData<List<Conversation>> getAllChatsData() { return allChats; }
    public LiveData<Conversation> getSingleChatData() { return singleChat; }
    public LiveData<String> getMessageResult() { return messageResult; }
    public LiveData<String> getErrorResult() { return errorResult; }

    // ===== Actions =====

    public void getAllChats() {
        conversationRepository.getAllChats().enqueue(new Callback<ListConversationResponse>() {
            @Override
            public void onResponse(Call<ListConversationResponse> call, Response<ListConversationResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("API_RESPONSE_CHAT", new Gson().toJson(response.body()));
                    allChats.setValue(response.body().getAllConversation());
                    messageResult.setValue("Lấy tất cả cuộc trò chuyện thành công");
                } else handleError(response);
            }

            @Override
            public void onFailure(Call<ListConversationResponse> call, Throwable t) {
                errorResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void getAllUnreadChats() {
        conversationRepository.getAllUnreadChats().enqueue(getListCallback("Lấy danh sách đoạn conversation chưa đọc thành công"));
    }

    public void getAllGroupChats() {
        conversationRepository.getAllGroupChats().enqueue(getListCallback("Lấy danh sách nhóm conversation thành công"));
    }

    public void getChatById(String conversationId) {
        conversationRepository.getChatById(conversationId).enqueue(getListCallback("Lấy thông tin đoạn conversation thành công"));
    }

    public void createChat(FriendIdRequest request) {
        conversationRepository.createChat(request).enqueue(new Callback<ConversationResponse>() {
            @Override
            public void onResponse(Call<ConversationResponse> call, Response<ConversationResponse> response) {
                if (response.isSuccessful()) {
                    singleChat.setValue(response.body().getConversation());
                    messageResult.setValue("Tạo đoạn conversation cá nhân thành công");
                } else handleError(response);
            }

            @Override
            public void onFailure(Call<ConversationResponse> call, Throwable t) {
                errorResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void createGroupChat(GroupChatRequest request) {
        conversationRepository.createGroupChat(request).enqueue(new Callback<ConversationResponse>() {
            @Override
            public void onResponse(Call<ConversationResponse> call, Response<ConversationResponse> response) {
                if (response.isSuccessful()) {
                    singleChat.setValue(response.body().getConversation());
                    messageResult.setValue("Tạo nhóm conversation thành công");
                } else handleError(response);
            }

            @Override
            public void onFailure(Call<ConversationResponse> call, Throwable t) {
                errorResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void updateChat(String conversationId, Conversation conversation) {
        conversationRepository.updateChat(conversationId, conversation).enqueue(getMessageCallback("Cập nhật nhóm conversation thành công"));
    }

    public void deleteChat(String conversationId) {
        conversationRepository.deleteChat(conversationId).enqueue(getMessageCallback("Xoá đoạn conversation thành công"));
    }

    public void deleteMessage(String messageId) {
        conversationRepository.deleteMessage(messageId).enqueue(getMessageCallback("Xoá đoạn message thành công"));
    }

    public void addMembers(String conversationId, ListMemberIdRequest request) {
        conversationRepository.addMemberToGroupChat(conversationId, request).enqueue(getMessageCallback("Thêm thành viên vào nhóm thành công"));
    }

    public void removeMember(String conversationId, UserIdRequest request) {
        conversationRepository.removeMemberFromGroupChat(conversationId, request).enqueue(getVoidCallback("Xoá thành viên khỏi nhóm thành công"));
    }

    public void joinGroup(UserIdRequest request) {
        conversationRepository.joinGroupChat(request).enqueue(getVoidCallback("Tham gia nhóm thành công"));
    }

    public void leaveGroup(UserIdRequest request) {
        conversationRepository.leaveGroupChat(request).enqueue(getVoidCallback("Rời khỏi nhóm thành công"));
    }

    public void muteGroup(UserIdRequest request) {
        conversationRepository.muteGroupChat(request).enqueue(getVoidCallback("Tắt thông báo nhóm thành công"));
    }

    public void unmuteGroup(UserIdRequest request) {
        conversationRepository.unmuteGroupChat(request).enqueue(getVoidCallback("Bật thông báo nhóm thành công"));
    }

    // ======= Helpers =======

    private Callback<ListConversationResponse> getListCallback(String successMsg) {
        return new Callback<ListConversationResponse>() {
            @Override
            public void onResponse(Call<ListConversationResponse> call, Response<ListConversationResponse> response) {
                if (response.isSuccessful()) {
                    allChats.setValue(response.body().getAllConversation());
                    messageResult.setValue(successMsg);
                } else handleError(response);
            }

            @Override
            public void onFailure(Call<ListConversationResponse> call, Throwable t) {
                errorResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        };
    }

    private Callback<MessageResponse> getMessageCallback(String successMsg) {
        return new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    messageResult.setValue(successMsg);
                } else handleError(response);
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                errorResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        };
    }

    private Callback<Void> getVoidCallback(String successMsg) {
        return new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    messageResult.setValue(successMsg);
                } else {
                    errorResult.setValue("Thao tác thất bại: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        };
    }

    private void handleError(Response<?> response) {
        try {
            MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
            errorResult.setValue("Lỗi: " + error.getMessage());
        } catch (Exception e) {
            errorResult.setValue("Lỗi không xác định: " + response.code());
        }
    }
}