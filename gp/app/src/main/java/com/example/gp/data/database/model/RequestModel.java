package com.example.gp.data.database.model;

public class RequestModel extends NotificationModel {
    private String senderId;

    public RequestModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
