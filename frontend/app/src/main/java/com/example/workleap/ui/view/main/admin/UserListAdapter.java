package com.example.workleap.ui.view.main.admin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.User;
import com.google.android.material.imageview.ShapeableImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private List<User> userList;
    private OnUserClickListener listener;

    public UserListAdapter(List<User> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public void updateList(List<User> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }
    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_adminprofile, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getUsername());
        holder.tvEmail.setText(user.getEmail());
        holder.tvRole.setText(user.getRole());
        holder.tvPhone.setText(user.getPhoneNumber());

        // Format createdAt (nếu kiểu Date)
        if (user.getCreatedAt() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = sdf.format(user.getCreatedAt());
            holder.tvCreatedAt.setText("Ngày tạo: " + formattedDate);
        } else {
            holder.tvCreatedAt.setText("Ngày tạo: --");
        }

        holder.tvStatus.setText(user.getStatus());
        String status = user.getStatus();
        if (status == null) status = "";
        switch (status) {
            case "ACTIVE":
                holder.tvStatus.setTextColor(Color.parseColor("#388E3C")); // xanh lá đậm
                break;
            case "LOCKED":
                holder.tvStatus.setTextColor(Color.parseColor("#FFA000")); // cam
                break;
            case "BANNED":
                holder.tvStatus.setTextColor(Color.parseColor("#D32F2F")); // đỏ
                break;
            default:
                holder.tvStatus.setTextColor(Color.GRAY); // mặc định
                break;
        }


        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar())
                .placeholder(R.drawable.ic_profile)
                .into(holder.imgAvatar);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imgAvatar;
        TextView tvName, tvEmail, tvRole, tvPhone, tvCreatedAt, tvStatus;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgUserAvatar);
            tvName = itemView.findViewById(R.id.tvUserName);
            tvEmail = itemView.findViewById(R.id.tvUserEmail);
            tvRole = itemView.findViewById(R.id.tvUserRole);
            tvPhone = itemView.findViewById(R.id.tvUserPhone);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
