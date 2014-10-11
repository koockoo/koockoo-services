package com.koockoo.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.ChatRoomDAO;
import com.koockoo.chat.model.db.Auth;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.db.Operator;
import com.koockoo.chat.model.ui.ChatRoomUI;
import com.koockoo.chat.model.ui.GuestUI;

@Service
public class ChatRoomService {

    @Autowired
    AuthService authService;

    @Autowired
    ChatRoomDAO dao;

    /**
     * Retrieve all pending conversations for operator 1. retrieve operator ref
     * from Auth. Only logged in operator can retrieve chatrooms. 2. get
     * operator entity by operator ref. 3. get all ChatRooms by the topic
     * assigned to operator. Operator can be assigned to exactly one topic. In
     * future this may change to multiple topics.
     */
    public List<ChatRoom> getPendingChatRooms(String token) {
        Auth auth = authService.authenticate(token);
        return getPendingChatRoomsByOperator(auth.getOperatorRef());
    }

    public List<ChatRoom> getPendingChatRoomsByOperator(String operatorId) {
        Operator o = dao.get(Operator.class, operatorId);
        List<ChatRoom> rooms = dao.getPendingByTopic(o.getTopicRef());
        return rooms;
    }

    /**
     * Open ChatRoom this will open a new ChatRoom. 
     * 1. Create a guest which requests communication 
     * 2. Create a ChatRoom
     */
    public ChatRoom acceptChatRoom(String roomRef, String operatorRef) {
        ChatRoom r = dao.get(roomRef);
        r.setState(ChatRoom.States.ACTIVE);
        r.addOperatorRef(operatorRef);
        return dao.save(r);
    }    

    /**
     * Open ChatRoom this will open a new ChatRoom. 
     * 1. Create a guest which requests communication 
     * 2. Create a ChatRoom
     */
    public ChatRoom openChatRoom(String displayName, String topicRef) {
        Guest g = new Guest();
        g.setDisplayName(displayName);
        dao.save(g);

        ChatRoom r = new ChatRoom();
        r.setGuestRef(g.getId());
        r.setTopicRef(topicRef);
        dao.save(r);

        return r;
    }

    /**
     * Close ChatRoom, no more messages are allowed on this chatroom.
     */
    public void closeChatRoom(String roomRef) {
        ChatRoom r = dao.get(roomRef);
        r.setState(ChatRoom.States.CLOSED);
        dao.save(r);
    }
  
    /**
     * Close ChatRoom, no more messages are allowed on this chatroom.
     */
    public ChatRoom getChatRoom(String roomRef) {
        return dao.get(roomRef); 
    }

}