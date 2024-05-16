package com.example.gp.Items;

import com.google.firebase.Timestamp;

import java.util.Date;

public class NotificationFactory {

    // Create following notification
    public static Notification createFollowNotification(String title, String message, Timestamp timestamp, String userId) {
        return new Notification(title, message, timestamp, Notification.NotificationType.FOLLOW, userId);
    }



    // Create post update notification
    public static Notification createFriendNotification(String title, String message, Timestamp timestamp, String userId) {
        return new Notification(title, message, timestamp, Notification.NotificationType.POST_UPDATE,userId);
    }
}
