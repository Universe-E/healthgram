package com.example.gp.Items;

public class Friend {
    private String id;
    private String nickname;

    private int avatar; // avatar id

    public Friend(String id, String nickname, int avatar) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public Friend() {

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
