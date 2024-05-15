package com.example.gp.data.database.model;

import com.google.firebase.Timestamp;

public class NotificationModel {
    private String message;
    private String username;
    private String notificationId;
    private String senderId;
    private String type;
    private Timestamp timestamp;
    private boolean isRead;

    public NotificationModel() {
    }

    public NotificationModel(String message, String username, String notificationId, String senderId, String type, Timestamp timestamp, boolean isRead) {
        this.message = message;
        this.username = username;
        this.notificationId = notificationId;
        this.senderId = senderId;
        this.type = type;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
