package com.example.gp.data.database.model;

import com.example.gp.Items.Friend;

/**
 * A friend data model for firebase firestore
 */
public class FriendModel {
    private String userId;
    private String nickname;

    public FriendModel() {
    }

    public FriendModel(Friend friend) {
        this.userId = friend.getId();
        this.nickname = friend.getNickname();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
