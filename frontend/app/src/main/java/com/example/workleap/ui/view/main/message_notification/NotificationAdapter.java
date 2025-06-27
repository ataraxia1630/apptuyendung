package com.example.workleap.ui.view.main.message_notification;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Notification;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;
    private OnNotificationClickListener listener;

    public interface OnNotificationClickListener {
        void onClick(Notification notification);
    }

    public NotificationAdapter(List<Notification> notificationList, OnNotificationClickListener listener) {
        this.notificationList = notificationList;
        this.listener = listener;
    }

    public void setNotifications(List<Notification> newList) {
        this.notificationList = newList;
        notifyDataSetChanged();
    }

    public Notification getNotificationAt(int position) {
        return notificationList.get(position);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notificationList != null ? notificationList.size() : 0;
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNotification;
        TextView txtTitle, txtMessage, txtTime;

        View cardView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNotification = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.tvTitle);
            txtMessage = itemView.findViewById(R.id.tvMessage);
            txtTime = itemView.findViewById(R.id.tvCreatedAt);
            cardView = itemView.findViewById(R.id.cardNotification);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onClick(notificationList.get(position));
                }
            });
        }

        public void bind(Notification notification) {
            txtTitle.setText(notification.getTitle());
            txtMessage.setText(notification.getMessage());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            txtTime.setText(sdf.format(notification.getCreated_at()));

            if("UNREAD".equalsIgnoreCase(notification.getStatus()))
            {
                cardView.setBackgroundColor(Color.parseColor("#E3F2FD"));
            }
            //imgNotification.setImageResource(R.drawable.ic_docfile);
        }
    }
}
