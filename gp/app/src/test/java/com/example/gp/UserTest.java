package com.example.gp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.text.format.DateFormat;

import com.example.gp.Items.Friend;
import com.example.gp.Items.Notification;
import com.example.gp.Items.Post;
import com.example.gp.Items.User;
import com.google.firebase.Timestamp;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Test user class
 * @author Zehua Kong
 */
public class UserTest {
    Timestamp currentDate = new Timestamp(new Date());

    @Test
    public void testUser() {
        User user = new User();
        user.setUserId("userId");
        user.setUsername("username");
        user.setEmail("user@example.com");

        assertEquals("userId", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals("user@example.com", user.getEmail());
    }

    @Test
    public void testUserConstructorWithFullParams() {
        Map<String, Friend> friendMap = new HashMap<>();
        Friend f1 = new Friend("f1","Alice",1);
        Friend f2 = new Friend("f2","Bob",2);
        friendMap.put("f1",f1);
        friendMap.put("f2",f2);

        Map<String, Notification> notificationMap = new HashMap<>();
        Notification n1 = new Notification("Vacation","go to Italy",currentDate, Notification.NotificationType.FOLLOW,"123");
        Notification n2 = new Notification("Vacation","go to Spain",currentDate, Notification.NotificationType.FOLLOW,"123");
        notificationMap.put("n1",n1);
        notificationMap.put("n2",n2);

        User user = new User("userId", "username", "user@example.com", "description",
                0, friendMap,  notificationMap);

        assertEquals("userId", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("description", user.getDescription());
        assertEquals(0, user.getAvatar());
        assertEquals(friendMap, user.getFriendMap());
        assertEquals(notificationMap, user.getNotificationMap());
    }

    @Test
    public void testUserConstructorWithRequiredParams() {
        User user = new User("userId", "username", "user@example.com");

        assertEquals("userId", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("set description", user.getDescription());
        assertEquals(0, user.getAvatar());
        assertNotNull(user.getFriendMap());
        assertNotNull(user.getNotificationMap());
    }
}
