package com.example.gp.Items;

import java.util.Date;

public class NotificationFactory {

    // Create following notification
    public static Notification createFollowNotification(String title, String message, CharSequence date, String userId) {
        return new Notification(title, message, date, Notification.NotificationType.FOLLOW, userId);
    }

    // Create mentioned notification
    public static Notification createMentionNotification(String title, String message, CharSequence date, String userId) {
        return new Notification(title, message, date, Notification.NotificationType.MENTION, userId);
    }

    // Create post update notification
    public static Notification createFriendNotification(String title, String message, CharSequence date,String userId) {
        return new Notification(title, message, date, Notification.NotificationType.POST_UPDATE,userId);
    }
}
