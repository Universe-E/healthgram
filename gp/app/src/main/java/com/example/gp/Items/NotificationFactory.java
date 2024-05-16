package com.example.gp.Items;

import com.google.firebase.Timestamp;

import java.util.Date;

public class NotificationFactory {

    /**
     * Create following notification
     * @param title: title of the notification
     * @param message: message of the notification
     * @param timestamp: timestamp of the notification
     * @param userId: user id of the notification
     * @return Notification: the created notification
     */
    public static Notification createFollowNotification(String title, String message, Timestamp timestamp, String userId) {
        return new Notification(title, message, timestamp, Notification.NotificationType.FOLLOW, userId);
    }

    /**
     * Create friend request notification
     * @param title: title of the notification
     * @param message: message of the notification
     * @param timestamp: timestamp of the notification
     * @param userId: user id of the notification
     * @return Notification: the created notification
     */
    public static Notification createFriendNotification(String title, String message, Timestamp timestamp, String userId) {
        return new Notification(title, message, timestamp, Notification.NotificationType.POST_UPDATE,userId);
    }
}
