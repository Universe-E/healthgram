package com.example.gp.data.database.model;

import com.example.gp.Items.FriendRequest;
import com.google.firebase.Timestamp;

public class FriendRequestModel {
    private String requestId;
    private String senderId;
    private String senderName;
    private String receiverId;
    private boolean isRead;
    private boolean isAccepted;
    private Timestamp requestTimestamp;

    public FriendRequestModel() {
    }

    public FriendRequestModel(FriendRequest friendRequest) {
        this.requestId = friendRequest.getRequestId();
        this.senderId = friendRequest.getSenderId();
        this.receiverId = friendRequest.getReceiverId();
        this.isRead = friendRequest.isRead();
        this.isAccepted = friendRequest.isAccepted();
        this.requestTimestamp = friendRequest.getRequestTimestamp();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Timestamp getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Timestamp requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public NotificationModel getNotificationModel() {
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setNotificationId(this.requestId);
        notificationModel.setMessage("New friend request");
        return notificationModel;
    }
}
