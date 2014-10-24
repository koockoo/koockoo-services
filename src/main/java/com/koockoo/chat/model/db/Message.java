package com.koockoo.chat.model.db;

import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import com.datastax.driver.mapping.annotation.Ttl;

@Ttl(300)
@Table(name="messages")
public class Message implements Comparable<Message> {
    
    @EmbeddedId
    private MessageKey key;
    private String authorRef;
    private int authorType = 0; //0- guest, 1- operator
    private String text;

    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public MessageKey getKey() {
        return key;
    }

    public void setKey(MessageKey key) {
        this.key = key;
    }
    
    @Override
    public int compareTo(Message o) {
        return key.getId().compareTo(o.getKey().getId());
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
}