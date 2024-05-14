package com.example.gp.Items;

import com.example.gp.data.database.model.NotificationModel;

import java.util.Date;

public class Notification {

    public enum NotificationType {
        FOLLOW, MENTION, POST_UPDATE
    }

    private String title;
    private String message;
    private CharSequence date;
    private NotificationType type;
    private String userId;

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


}


