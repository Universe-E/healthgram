package com.example.gp.data.database.model;

import com.example.gp.Items.Post;

import java.util.List;

public class UserModel {
    private String username;
    private String email;
    private String avatarUUID;
    private List<FriendModel> myFriends;
    private List<PostModel> myPosts;
    private List<RequestModel> myRequests;

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

    public List<RequestModel> getMyRequests() {
        return myRequests;
    }

    public void setMyRequests(List<RequestModel> myRequests) {
        this.myRequests = myRequests;
    }
}
