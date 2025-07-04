package com.example.workleap.ui.view.main.message_notification;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.Conversation;
import com.example.workleap.data.model.entity.ConversationUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationUserAdapter extends RecyclerView.Adapter<ConversationUserAdapter.ConversationUserViewHolder> {

    private final Context context;
    private List<ConversationUser> conversationUserList;
    private final OnConversationClickListener listener;
    private Map<String, String> avatarUrlMap = new HashMap<>();
    private String logoFilePath;
    public interface OnConversationClickListener {
        void onConversationClick(ConversationUser conversationUser);
    }

    public ConversationUserAdapter(Context context, List<ConversationUser> conversationUserList, OnConversationClickListener listener) {
        this.context = context;
        this.conversationUserList = conversationUserList;
        this.listener = listener;
    }

    public void setConversationList(List<ConversationUser> conversationUserList) {
        this.conversationUserList = conversationUserList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ConversationUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationUserViewHolder holder, int position) {
        ConversationUser conversationUser = conversationUserList.get(position);

        int indexOfUser;
        if(conversationUser.getUserId().equals(conversationUser.getConversation().getMembers().get(0).getUserId()))
            indexOfUser = 1;
        else
            indexOfUser = 0;

        // Set tên đoạn chat
        String displayName = conversationUser.getConversation().getMembers().get(indexOfUser).getUser().getUsername() != null ? conversationUser.getConversation().getMembers().get(indexOfUser).getUser().getUsername() : "Đoạn chat";
        holder.txtName.setText(displayName);

        // Role of user
        String role = conversationUser.getConversation().getMembers().get(indexOfUser).getUser().getEmail() != null ? conversationUser.getConversation().getMembers().get(indexOfUser).getUser().getEmail() : "Messages...";
        holder.txtLastMessage.setText(role);

        //Xu li logo chat
        logoFilePath = conversationUser.getConversation().getMembers().get(indexOfUser).getUser().getAvatar(); // dùng làm key
        if(logoFilePath != null)
        {
            String imageUrl = avatarUrlMap.get(logoFilePath);
            if(holder.imgAvatar == null)
                Log.d("MessageAdapter", "Avatar is null");
            if (imageUrl != null && holder.imgAvatar != null) {
                Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imgAvatar);
            }
        }

        // Ẩn/hiện số lượng tin chưa đọc
        /*if (conversation.get() > 0) {
            holder.txtUnreadCount.setVisibility(View.VISIBLE);
            holder.txtUnreadCount.setText(String.valueOf(conversation.getUnreadCount()));
        } else {
            holder.txtUnreadCount.setVisibility(View.GONE);
        }*/

        // Avatar: nhóm thì icon nhóm, cá nhân thì có thể load ảnh từ user (placeholder ở đây)
        if (conversationUser.getConversation().isGroup()) {
            //holder.imgAvatar.setImageResource(R.drawable.ic_group_avatar);
        } else {
            // Tạm thời dùng icon người
            //holder.imgAvatar.setImageResource(R.drawable.ic_user);
            // Glide nếu có ảnh avatar:
            // Glide.with(context).load(conversation.getAvatarUrl()).into(holder.imgAvatar);
        }

        // Bắt sự kiện click
        holder.itemView.setOnClickListener(v -> listener.onConversationClick(conversationUser));
    }

    @Override
    public int getItemCount() {
        return conversationUserList != null ? conversationUserList.size() : 0;
    }

    public static class ConversationUserViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName, txtLastMessage, txtUnreadCount;

        public ConversationUserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtName = itemView.findViewById(R.id.txtConversationName);
            txtLastMessage = itemView.findViewById(R.id.txtLastMessage);
            txtUnreadCount = itemView.findViewById(R.id.txtUnreadCount);
        }
    }

    public void setImageUrlMap(Map<String, String> map) {
        this.avatarUrlMap = map;
        notifyDataSetChanged();
    }
}
