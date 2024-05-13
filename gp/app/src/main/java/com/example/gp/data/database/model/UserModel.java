package com.example.gp.data.database.model;

import androidx.annotation.NonNull;

import com.example.gp.Items.User;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String username;
    private String email;
    private String avatarUUID;
    private List<FriendModel> myFriends;
    private List<PostModel> myPosts;
    private List<NotificationModel> myNotifications;

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

    public String getAvatarUUID() {
        return avatarUUID;
    }

    public void setAvatarUUID(String avatarUUID) {
        this.avatarUUID = avatarUUID;
    }

    public List<FriendModel> getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(List<FriendModel> myFriends) {
        this.myFriends = myFriends;
    }

    public List<PostModel> getMyPosts() {
        return myPosts;
    }

    public void setMyPosts(List<PostModel> myPosts) {
        this.myPosts = myPosts;
    }

    public List<NotificationModel> getMyNotifications() {
        return myNotifications;
    }

    public void setMyNotifications(List<NotificationModel> myNotifications) {
        this.myNotifications = myNotifications;
    }

    public void setModelFromUser(@NonNull User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUUID = user.getAvatarUUID();
        this.myFriends = new ArrayList<>();
        this.myPosts = new ArrayList<>();
        this.myNotifications = new ArrayList<>();
    }
}
