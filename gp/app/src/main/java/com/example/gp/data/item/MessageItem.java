package com.example.gp.data.item;

/**
 * @desc Send message instance
 * @author Zehua Kong
 */
public class MessageItem{
    // Text
    public static final int MESSAGE_TYPE_TEXT = 1;
    // image
    public static final int MESSAGE_TYPE_IMG = 2;
    // file
    public static final int MESSAGE_TYPE_FILE = 3;
    // Record
    public static final int MESSAGE_TYPE_RECORD = 4;

    private int msgType;// message type
    private String name;// username that message from
    private long date;// message date
    private String message;// message content
    private int headImg;// head icon
    private boolean isComMeg = true;// is received

    private int isNew;
    private int voiceTime;

    public MessageItem() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param msgType message type
     * @param name username message from
     * @param date message date
     * @param message message content
     * @param headImg head icon
     * @param isComMeg is message received
     * @param isNew is new message
     * @param voiceTime voice time, 0 if no voice recorded
     */
    public MessageItem(int msgType, String name, long date, String message,
            int headImg, boolean isComMeg, int isNew, int voiceTime) {
        super();
        this.msgType = msgType;
        this.name = name;
        this.date = date;
        this.message = message;
        this.headImg = headImg;
        this.isComMeg = isComMeg;
        this.isNew = isNew;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHeadImg() {
        return headImg;
    }

    public void setHeadImg(int headImg) {
        this.headImg = headImg;
    }

    public boolean isComMeg() {
        return isComMeg;
    }

    public void setComMeg(boolean isComMeg) {
        this.isComMeg = isComMeg;
    }

    public static int getMessageTypeText() {
        return MESSAGE_TYPE_TEXT;
    }

    public static int getMessageTypeImg() {
        return MESSAGE_TYPE_IMG;
    }

    public static int getMessageTypeFile() {
        return MESSAGE_TYPE_FILE;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

}
