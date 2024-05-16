package com.example.gp.Items;

import com.example.gp.data.database.model.NotificationModel;
import com.google.firebase.Timestamp;

/**
 * Notification class
 * The class to store the information of a notification
 */
public class Notification {

    public enum NotificationType {
        FOLLOW,  POST_UPDATE, FRIEND_REQUEST
    }

    private String title;
    private String message;
    private NotificationType type;
    private String userId;
    private String notificationId;
    private boolean isRead;
    private Timestamp timestamp;
    private String senderName;

    /**
     * Constructor, now includes notification type and user ID
     */
    public Notification(String title, String message, Timestamp timestamp, NotificationType type, String userId) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.userId = userId;
    }

    public Notification(NotificationModel notificationModel) {
        this.message = notificationModel.getMessage();
        this.notificationId = notificationModel.getNotificationId();
        this.userId = notificationModel.getSenderId();
        this.isRead = notificationModel.getRead();
        this.timestamp = notificationModel.getTimestamp();
        this.senderName = notificationModel.getUsername();
        if (notificationModel.getType() == null) {
            return;
        }
        if (notificationModel.getType().equals("follow")) {
            this.type = NotificationType.FOLLOW;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}


