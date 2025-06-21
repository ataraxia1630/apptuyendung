package com.example.workleap.ui.view.main.message_notification;

import android.content.Context;
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

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    private final Context context;
    private List<Conversation> conversationList;
    private final OnConversationClickListener listener;

    public interface OnConversationClickListener {
        void onConversationClick(Conversation conversation);
    }

    public ConversationAdapter(Context context, List<Conversation> conversationList, OnConversationClickListener listener) {
        this.context = context;
        this.conversationList = conversationList;
        this.listener = listener;
    }

    public void setConversationList(List<Conversation> conversationList) {
        this.conversationList = conversationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        Conversation conversation = conversationList.get(position);

        // Set tên đoạn chat
        String displayName = conversation.getName() != null ? conversation.getName() : "Đoạn chat";
        holder.txtName.setText(displayName);

        // Set tin nhắn gần nhất (chỉ placeholder nếu backend chưa có)
        holder.txtLastMessage.setText("Tin nhắn gần nhất...");

        // Ẩn/hiện số lượng tin chưa đọc
        /*if (conversation.get() > 0) {
            holder.txtUnreadCount.setVisibility(View.VISIBLE);
            holder.txtUnreadCount.setText(String.valueOf(conversation.getUnreadCount()));
        } else {
            holder.txtUnreadCount.setVisibility(View.GONE);
        }*/

        // Avatar: nhóm thì icon nhóm, cá nhân thì có thể load ảnh từ user (placeholder ở đây)
        if (conversation.isGroup()) {
            //holder.imgAvatar.setImageResource(R.drawable.ic_group_avatar);
        } else {
            // Tạm thời dùng icon người
            //holder.imgAvatar.setImageResource(R.drawable.ic_user);
            // Glide nếu có ảnh avatar:
            // Glide.with(context).load(conversation.getAvatarUrl()).into(holder.imgAvatar);
        }

        // Bắt sự kiện click
        holder.itemView.setOnClickListener(v -> listener.onConversationClick(conversation));
    }

    @Override
    public int getItemCount() {
        return conversationList != null ? conversationList.size() : 0;
    }

    public static class ConversationViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName, txtLastMessage, txtUnreadCount;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtName = itemView.findViewById(R.id.txtConversationName);
            txtLastMessage = itemView.findViewById(R.id.txtLastMessage);
            txtUnreadCount = itemView.findViewById(R.id.txtUnreadCount);
        }
    }
}
