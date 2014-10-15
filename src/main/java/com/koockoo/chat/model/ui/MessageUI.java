package com.koockoo.chat.model.ui;

import java.util.Date;

public class MessageUI {
    
    private String authorRef;
    private int authorType = 0; //0- guest, 1- operator
    private String authorName;
    private String text;
    private Date timestamp; //timeuuid representation for UI. Derived from id. 

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}