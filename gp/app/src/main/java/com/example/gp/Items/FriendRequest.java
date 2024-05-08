package com.example.gp.Items;

import com.google.firebase.Timestamp;

public class FriendRequest {
    private String RequestId;
    private String senderId;
    private String receiverId;
    private boolean isRead;
    private Timestamp requestTimestamp;

    /**
     * DO NOT USE THIS CONSTRUCTOR
     * DO NOT DELETE THIS CONSTRUCTOR
     */
    public FriendRequest() {
    }

    /**
     * Only use this constructor
     * Don't use getters and setters
     * @param receiverId
     */
    public FriendRequest(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getRequestId() {
        return RequestId;
    }

    /**
     * Getters and Setters
     * <p>
     * DO NOT MODIFY THIS
     *
     */
    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Timestamp getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Timestamp requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }
    // END OF DO NOT MODIFY THIS
    // End of getters and setters
}
