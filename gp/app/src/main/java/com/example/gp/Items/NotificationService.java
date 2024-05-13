package com.example.gp.Items;

import java.util.Date;

public class NotificationService {

    public void processNotifications() {
        Notification followNotif = NotificationFactory.createFollowNotification(
                "New follower",
                "John Doe has started following you.",
                "2024-05-12",
                "user123"
        );

        Notification mentionNotif = NotificationFactory.createMentionNotification(
                "@Mention",
                "You were mentioned by Alice in a comment.",
                "2024-04-12",
                "user456"
        );

        Notification postUpdateNotif = NotificationFactory.createFriendNotification(
                "New Post",
                "A new post from someone you follow.",
                "2024-03-21",
                "user789"
        );

        // Here you can add notifications to the database or perform other processing
    }
}
