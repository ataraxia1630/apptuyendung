package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.Notification;

import java.util.List;

public class ListNotificationResponse {
    String message;

    List<Notification> notifications;

    public ListNotificationResponse() {
    }

    public ListNotificationResponse(String message, List<Notification> notifications) {
        this.message = message;
        this.notifications = notifications;
    }

    public String getMessage() {
        return message;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
