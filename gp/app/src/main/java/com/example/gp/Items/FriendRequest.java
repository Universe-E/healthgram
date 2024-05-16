package com.example.gp.Items;

import com.example.gp.data.database.model.FriendRequestModel;
import com.google.firebase.Timestamp;

/**
 * FriendRequest class
 * The class to store the information of a friend request
 *
 * @author Han Bao
 */
public class FriendRequest {
    private String RequestId;
    private String senderId;
    private String senderName;
    private String receiverId;
    private boolean isRead;
    private boolean isAccepted;
    private Timestamp requestTimestamp;

    /**
     * DO NOT USE THIS CONSTRUCTOR
     */
    public FriendRequest() {
    }

    /**
     * Initialize friend request with model
     */
    public FriendRequest(FriendRequestModel friendRequestModel) {
        this.RequestId = friendRequestModel.getRequestId();
        this.senderId = friendRequestModel.getSenderId();
        this.senderName = friendRequestModel.getSenderName();
        this.receiverId = friendRequestModel.getReceiverId();
        this.isRead = friendRequestModel.isRead();
        this.isAccepted = friendRequestModel.isAccepted();
        this.requestTimestamp = friendRequestModel.getRequestTimestamp();
    }

    /**
     * Only use this constructor
     * Don't use getters and setters
     */
    public FriendRequest(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getRequestId() {
        return RequestId;
    }

    /**
     * Getters and Setters
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    // END OF DO NOT MODIFY THIS
    // End of getters and setters
}
