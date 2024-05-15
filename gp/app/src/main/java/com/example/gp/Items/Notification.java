package com.example.gp.Items;

import com.example.gp.data.database.model.NotificationModel;

import java.util.Date;

public class Notification {

    public enum NotificationType {
        FOLLOW, MENTION, POST_UPDATE, FRIEND_REQUEST
    }

    private String title;
    private String message;
    private CharSequence date;
    private NotificationType type;
    private String userId;
    private String notificationId;
    private boolean isRead;

    // Constructor, now includes notification type and user ID
    public Notification(String title, String message, CharSequence date, NotificationType type, String userId) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.type = type;
        this.userId = userId;
    }

    public Notification(NotificationModel notificationModel) {
        this.message = notificationModel.getMessage();
        this.notificationId = notificationModel.getNotificationId();
        this.userId = notificationModel.getSenderId();
        this.isRead = notificationModel.getRead();
        if (notificationModel.getType() == null) {
            return;
        }
        if (notificationModel.getType().equals("follow")) {
            this.type = NotificationType.FOLLOW;
        }
        else if (notificationModel.getType().equals("mention")) {
            this.type = NotificationType.MENTION;
        }
        else if (notificationModel.getType().equals("post_update")) {
            this.type = NotificationType.POST_UPDATE;
        }
        else if (notificationModel.getType().equals("friend_request")) {
            this.type = NotificationType.FRIEND_REQUEST;
        }
    }

    // Getter and Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CharSequence getDate() {
        return date;
    }

    public void setDate(CharSequence date) {
        this.date = date;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}


