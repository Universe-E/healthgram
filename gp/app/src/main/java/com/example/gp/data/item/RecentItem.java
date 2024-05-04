package com.example.gp.data.item;

/**
 * @desc Received message data
 * @author Zehua Kong
 */
public class RecentItem implements Comparable<RecentItem> {

    private String userId;
    private int headImg;// head icon
    private String name;// username that message from
    private String message;// message content
    private int newNum;// new message amount
    private long time;// message time
    private int msgType;// message type
    private int voiceTime;// voice time

    public RecentItem() {
    }

    /**
     * 
     * @param messageType message type
     * @param userId user id
     * @param headImg user head icon
     * @param name user name
     * @param message message content
     * @param newNum new message amount
     * @param time message time
     * @param voiceTime voice time
     */
    public RecentItem(int messageType, String userId, int headImg, String name,
            String message, int newNum, long time, int voiceTime) {
        super();

        this.userId = userId;
        this.headImg = headImg;
        this.name = name;
        this.message = message;
        this.newNum = newNum;
        this.time = time;
        this.msgType = messageType;
        this.voiceTime = voiceTime;
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        this.voiceTime = voiceTime;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getHeadImg() {
        return headImg;
    }

    public void setHeadImg(int headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNewNum() {
        return newNum;
    }

    public void setNewNum(int newNum) {
        this.newNum = newNum;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code = (31 * (this.userId.hashCode())) >> 2;
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o instanceof RecentItem item) {
            return item.userId.equals(this.userId);
        }
        return false;
    }

    @Override
    public int compareTo(RecentItem another) {
        return (int) (another.time - this.time);
    }

}
