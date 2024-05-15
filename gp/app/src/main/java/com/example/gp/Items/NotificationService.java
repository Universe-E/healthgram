package com.example.gp.Items;

import com.google.firebase.Timestamp;

import java.util.Date;

public class NotificationService {

    public void processNotifications() {
        Notification followNotif = NotificationFactory.createFollowNotification(
                "New follower",
                "John Doe has started following you.",
                Timestamp.now(),
                "user123"
        );

        Notification mentionNotif = NotificationFactory.createMentionNotification(
                "@Mention",
                "You were mentioned by Alice in a comment.",
                Timestamp.now(),
                "user456"
        );

        Notification postUpdateNotif = NotificationFactory.createFriendNotification(
                "New Post",
                "A new post from someone you follow.",
                Timestamp.now(),
                "user789"
        );

        // Here you can add notifications to the database or perform other processing
    }
}
