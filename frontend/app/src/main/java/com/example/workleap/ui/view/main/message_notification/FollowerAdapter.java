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
import com.example.workleap.data.model.entity.Follower;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder> {

    private final Context context;
    private List<Follower> followerList;
    private Map<String, String> avatarUrlMap = new HashMap<>();
    private String logoFilePath;
    private final OnFollowerClickListener listener;

    public interface OnFollowerClickListener {
        void onFollowerClick(Follower follower);
    }

    public FollowerAdapter(Context context, List<Follower> followerList, OnFollowerClickListener listener) {
        this.context = context;
        this.followerList = followerList;
        this.listener = listener;
    }

    public void setFollowerList(List<Follower> followerList) {
        this.followerList = followerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_follower, parent, false);
        return new FollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerViewHolder holder, int position) {
        Follower follower = followerList.get(position);

        // Set tên đoạn chat
        String displayName = follower.getFollowedUser() != null ? follower.getFollowedUser().getUsername() : "Đoạn chat";
        holder.txtName.setText(displayName);
        holder.txtRole.setText(follower.getFollowedUser().getRole());
        holder.txtEmail.setText(follower.getFollowedUser().getEmail());

        //Xu li logo follower
        logoFilePath = follower.getFollowedUser().getAvatar(); // dùng làm key
        if(logoFilePath != null)
        {
            String imageUrl = avatarUrlMap.get(logoFilePath);
            if(holder.imgAvatar == null)
                Log.d("FollowerAdapter", "Avatar is null");
            if (imageUrl != null && holder.imgAvatar != null) {
                Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imgAvatar);
            }
        }

        // Bắt sự kiện click
        holder.itemView.setOnClickListener(v -> listener.onFollowerClick(follower));
    }

    @Override
    public int getItemCount() {
        return followerList != null ? followerList.size() : 0;
    }

    public static class FollowerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName, txtRole, txtEmail;

        public FollowerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgSenderAvatar);
            txtName = itemView.findViewById(R.id.tvUsername);
            txtRole = itemView.findViewById(R.id.tvRole);
            txtEmail = itemView.findViewById(R.id.tvEmail);
        }
    }

    public void setImageUrlMap(Map<String, String> map) {
        this.avatarUrlMap = map;
        notifyDataSetChanged();
    }
}
