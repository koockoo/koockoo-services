package com.koockoo.chat.model.db;

import java.util.UUID;

import javax.persistence.Column;

public class MessageKey {
    
    private String chatRoomId;
    
    @Column(columnDefinition="timeuuid")
    private UUID id = java.util.UUID.fromString(new com.eaio.uuid.UUID().toString());
 
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
