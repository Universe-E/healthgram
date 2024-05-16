package com.example.gp.Items;

import java.util.HashMap;
import java.util.Map;

/**
 * Class User
 * @author Zehua Kong
 * {@code @editor} Han Bao
 * @version 1.1
 * @since 2024-05-06
 */
public class User {
    private static String userId;
    private static String username;
    private static String email;
    private static String description;
    private static int avatar;
    private static String avatarUUID;
    private static Map<String, Friend> friendMap;
    private static Map<String, Notification> notificationMap;

    /**
     * Constructor with full parameter
     *
     * @param userId          user id
     * @param username        user name
     * @param email           user email
     * @param description     user description
     * @param avatar          user avatar
     * @param friendMap       user friend map
     * @param notificationMap user notification map
     */
    public User(String userId, String username, String email, String description,
                int avatar, Map<String, Friend> friendMap,
                Map<String, Notification> notificationMap) {
        User.userId = userId;
        User.username = username;
        User.email = email;
        User.description = description;
        User.avatar = avatar;
        User.friendMap = friendMap;
        User.notificationMap = notificationMap;
    }

    /**
     * Constructor with required parameter
     */
    public User(String userId, String username, String email) {
        User.userId = userId;
        User.username = username;
        User.email = email;
        User.description = "set description";
        User.avatar = 0;
        User.friendMap = new HashMap<>();
        User.notificationMap = new HashMap<>();
    }

    /**
     * DO NOT DELETE THIS CONSTRUCTOR
     */
    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        User.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        User.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        User.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        User.description = description;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        User.avatar = avatar;
    }

    /**
     * Get friend map
     */
    public Map<String, Friend> getFriendMap() {
        return friendMap;
    }

    public void setFriendMap(Map<String, Friend> friendMap) {
        User.friendMap = friendMap;
    }

    public Map<String, Notification> getNotificationMap() {
        return notificationMap;
    }

    public void setNotificationMap(Map<String, Notification> notificationMap) {
        User.notificationMap = notificationMap;
    }

    public void setAvatarUUID(String avatarUUID) {
        User.avatarUUID = avatarUUID;
    }

    public String getAvatarUUID() {
        return avatarUUID;
    }
}
