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
    private String userId;
    private String username;
    private String email;
    private String description;
    private String avatar;
    private Map<String, Friend> friendMap;
    private Map<String, Post> postMap;
    private Map<String, Notification> notificationMap;

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
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.description = description;
        this.avatar = avatar;
        this.friendMap = friendMap;
        this.postMap = postMap;
        this.notificationMap = notificationMap;
    }

    /**
     * Constructor with required parameter
     */
    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.description = "set description";
        this.avatar = "set avatar";
        this.friendMap = new HashMap<>();
        this.postMap = new HashMap<>();
        this.notificationMap = new HashMap<>();
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Map<String, Friend> getFriendMap() {
        return friendMap;
    }

    public void setFriendMap(Map<String, Friend> friendMap) {
        this.friendMap = friendMap;
    }

    public Map<String, Post> getPostMap() {
        return postMap;
    }

    public void setPostMap(Map<String, Post> postMap) {
        this.postMap = postMap;
    }

    public Map<String, Notification> getNotificationMap() {
        return notificationMap;
    }

    public void setNotificationMap(Map<String, Notification> notificationMap) {
        this.notificationMap = notificationMap;
    }
}
