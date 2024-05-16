package com.example.gp.Items;

import com.google.firebase.Timestamp;

import java.util.Date;

/**
 * NotificationFactory class
 * The class to create notifications
 */
public class NotificationService {

    /**
     * Process notifications
     */
    public void processNotifications() {
        Notification followNotif = NotificationFactory.createFollowNotification(
                "New follower",
                "John Doe has started following you.",
                Timestamp.now(),
                "user123"
        );

        Notification postUpdateNotif = NotificationFactory.createFriendNotification(
                "New Post",
                "A new post from someone you follow.",
                Timestamp.now(),
                "user789"
        );
    }
}
