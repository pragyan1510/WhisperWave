package com.example.whisperwave.Models;

public class messages {
    String Uid, messages , messageID;
    Long timestamp;

    public messages(String uid, String messages, Long timestamp) {
        Uid = uid;
        this.messages = messages;
        this.timestamp = timestamp;
    }

    public messages(String uid, String messages) {
        Uid = uid;
        this.messages = messages;
    }

    public messages(){

    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
