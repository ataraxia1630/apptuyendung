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
import com.example.workleap.data.model.entity.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final Context context;
    private List<Message> messageList;
    private Map<String, String> avatarUrlMap = new HashMap<>();
    private String logoFilePath;
    private final OnMessageClickListener listener;

    public interface OnMessageClickListener {
        void onMessageClick(Message message);
    }

    public MessageAdapter(Context context, List<Message> messageList, OnMessageClickListener listener) {
        this.context = context;
        this.messageList = messageList;
        this.listener = listener;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);

        // Set tên đoạn chat
        String displayName = message.getSender().getUsername() != null ? message.getSender().getUsername() : "Đoạn chat";
        holder.txtName.setText(displayName);
        holder.txtContent.setText(message.getContent());
        holder.txtDateTime.setText(message.getSent_at());

        //Xu li logo comment
        logoFilePath = message.getSender().getAvatar(); // dùng làm key
        if(logoFilePath != null)
        {
            String imageUrl = avatarUrlMap.get(logoFilePath);
            if(holder.imgAvatar == null)
                Log.d("MessageAdapter", "Avatar is null");
            if (imageUrl != null && holder.imgAvatar != null) {
                Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imgAvatar);
            }
        }

        // Bắt sự kiện click
        holder.itemView.setOnClickListener(v -> listener.onMessageClick(message));
    }

    @Override
    public int getItemCount() {
        return messageList != null ? messageList.size() : 0;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName, txtContent, txtDateTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgSenderAvatar);
            txtName = itemView.findViewById(R.id.txtSenderName);
            txtContent = itemView.findViewById(R.id.txtMessageContent);
            txtDateTime = itemView.findViewById(R.id.txtTimestamp);
        }
    }

    public void setImageUrlMap(Map<String, String> map) {
        this.avatarUrlMap = map;
        notifyDataSetChanged();
    }
}
