package com.koockoo.chat.model.db;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Transient;

public class MessageKey {
    
    private String chatRoomId;
    
    @Column(columnDefinition="timeuuid")
    private UUID id = java.util.UUID.fromString(new com.eaio.uuid.UUID().toString());
 
    @Transient
    private Date timestamp; //timeuuid representation for UI. Derived from id. 
    
    public MessageKey() {
        id = java.util.UUID.fromString(new com.eaio.uuid.UUID().toString());
        timestamp = new Date(id.timestamp());
    }
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
        this.timestamp = new Date(id.timestamp());
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
