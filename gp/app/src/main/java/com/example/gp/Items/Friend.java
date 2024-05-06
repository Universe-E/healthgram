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

    public String getName(){
        return nickname;
    }

    public int getAvatar(){
        return avatar;
    }

}
