package com.example.workleap.ui.view.main.message_notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final Context context;
    private List<Message> messageList;
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
}
