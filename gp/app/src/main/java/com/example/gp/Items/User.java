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
    private static String avatar;
    private static Map<String, Friend> friendMap;
    private static Map<String, Post> postMap;
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
     * @param postMap         user post map
     * @param notificationMap user notification map
     */
    public User(String userId, String username, String email, String description,
                String avatar, Map<String, Friend> friendMap, Map<String, Post> postMap,
                Map<String, Notification> notificationMap) {
        User.userId = userId;
        User.username = username;
        User.email = email;
        User.description = description;
        User.avatar = avatar;
        User.friendMap = friendMap;
        User.postMap = postMap;
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
        User.avatar = "set avatar";
        User.friendMap = new HashMap<>();
        User.postMap = new HashMap<>();
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        User.avatar = avatar;
    }

    public Map<String, Friend> getFriendMap() {
        return friendMap;
    }

    public void setFriendMap(Map<String, Friend> friendMap) {
        User.friendMap = friendMap;
    }

    public Map<String, Post> getPostMap() {
        return postMap;
    }

    public void setPostMap(Map<String, Post> postMap) {
        User.postMap = postMap;
    }

    public Map<String, Notification> getNotificationMap() {
        return notificationMap;
    }

    public void setNotificationMap(Map<String, Notification> notificationMap) {
        User.notificationMap = notificationMap;
    }

    public String getAvatarUUID() {
        return avatar;
    }
}
