package com.example.gp.data.database.model;

import androidx.annotation.NonNull;

import com.example.gp.Items.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A user model for Firebase Firestore
 */
public class UserModel {
    private String username;
    private String email;
    private String userId;
    private String avatarUUID;
    private Map<String, FriendModel> myFriends;
    private Map<String, NotificationModel> myNotifications;

    public UserModel() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarUUID() {
        return avatarUUID;
    }

    public void setAvatarUUID(String avatarUUID) {
        this.avatarUUID = avatarUUID;
    }

    public Map<String, FriendModel> getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(Map<String, FriendModel> myFriends) {
        this.myFriends = myFriends;
    }

    public Map<String, NotificationModel> getMyNotifications() {
        return myNotifications;
    }

    public void setMyNotifications(Map<String, NotificationModel> myNotifications) {
        this.myNotifications = myNotifications;
    }

    public void setModelFromUser(@NonNull User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUUID = user.getAvatarUUID();
        this.myFriends = new HashMap<>();
        this.myNotifications = new HashMap<>();
    }
}
