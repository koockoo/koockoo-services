package com.koockoo.chat.model.ui;


public class MessageUI {
    
    private String id;
    private String chatRoomId;
    private String authorRef;
    private int authorType = 0; //0- guest, 1- operator
    private String authorName;
    private String text;
    private String utcDateTime; // ISO formatted time
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorRef() {
        return authorRef;
    }
    public void setAuthorRef(String authorRef) {
        this.authorRef = authorRef;
    }
    public int getAuthorType() {
        return authorType;
    }
    public void setAuthorType(int authorType) {
        this.authorType = authorType;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtcDateTime() {
        return utcDateTime;
    }

    public void setUtcDateTime(String utcDateTime) {
        this.utcDateTime = utcDateTime;
    }
}