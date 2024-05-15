package com.example.gp.data.database.model;

public class NotificationModel {
    private String message;
    private String notificationId;
    private String senderId;
    private String type;
    private boolean isRead;

    public NotificationModel() {
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
}
