package com.example.gp.Items;

import com.example.gp.data.database.model.FriendModel;

/**
 * Friend class
 * The class to store the information of a friend
 */
public class Friend {
    private String id;
    private String nickname;

    private int avatar; // avatar id

    public Friend(String id, String nickname, int avatar) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public Friend(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    /**
     * DO NOT DELETE THIS CONSTRUCTOR
     */
    public Friend() {
    }

    public Friend(FriendModel value) {
        this.id = value.getUserId();
        this.nickname = value.getNickname();
        this.avatar = 0;
    }

    public String getName(){
        return nickname;
    }

    public int getAvatar(){
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
