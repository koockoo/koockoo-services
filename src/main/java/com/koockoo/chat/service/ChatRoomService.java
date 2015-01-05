package com.koockoo.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.ChatRoomDAO;
import com.koockoo.chat.model.db.Auth;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.db.Operator;

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
        return dao.getPendingByTopic(auth.getTopicRef());
    }

    public List<ChatRoom> getPendingChatRoomsByOperator(String operatorId) {
        Operator o = dao.get(Operator.class, operatorId);
        List<ChatRoom> rooms = dao.getPendingByTopic(o.getTopicRef());
        return rooms;
    }


    /**
     * Retrieve all active conversations for operator 
     */
    public List<ChatRoom> getActiveChatRooms(String token) {
        Auth auth = authService.authenticate(token);
        return getActiveChatRoomsByOperator(auth.getOperatorRef());
    }    

    /**
     * Retrieve all active conversations for operator 
     */
    public List<ChatRoom> getActiveChatRoomsByOperator(String operatorRef) {
        Operator oper = dao.get(Operator.class, operatorRef);
        return dao.getActiveByOperator(oper);
    } 
    
    /**
     * Operator accepts chat request. 
     */
    public ChatRoom acceptChatRoom(String roomRef, String operatorRef) {
        ChatRoom r = dao.get(roomRef);
        r.setState(ChatRoom.States.ACTIVE);
        r.addOperatorRef(operatorRef);
        r = dao.save(r);
        
        // append active room ref to operator. will be removed whenever client exits chat
        // in case of unexpected issue clean up will be done by other operations 
        dao.appendAsync(operatorRef, Operator.class, "chatRoomRefs", r.getId());
        return r;
    }    

    /**
     * Open ChatRoom for the topic. This will 
     * 1. Create a guest which requests communication 
     * 2. Create a ChatRoom
     */
    public ChatRoom openChatRoomByTopic(String displayName, String topicRef) {
        Guest g = new Guest();
        g.setDisplayName(displayName);
        dao.save(g);
        return createChatRoom(topicRef, g.getId());
    }

    /**
     * open chatroom by the token. The guest is already registered.
     * all required data will be retrieved from auth token.
     * @param token - auth ref
     * @return ChatRoom
     */
    public ChatRoom openChatRoomByToken(String token) {
        Auth auth = authService.authenticate(token);
        return createChatRoom(auth.getTopicRef(), auth.getGuestRef());
    }
    
    /**
     * open chatroom by the token. The guest is already registered.
     * all required data will be retrieved from auth token.
     * @param token - auth ref
     * @return ChatRoom
     */
    public ChatRoom createChatRoom(String topicRef, String guestRef) {
        ChatRoom r = new ChatRoom();
        r.setGuestRef(guestRef);
        r.setTopicRef(topicRef);
        r = dao.save(r);
        dao.updateValueAsync(guestRef, Guest.class, "chatRoomRef", r.getId());
        return r;
    }
    
    /**
     * Close ChatRoom, no more messages are allowed on this chatroom.
     */
    public void closeChatRoom(String roomRef) {
        ChatRoom r = dao.get(roomRef);
        r.setState(ChatRoom.States.CLOSED);
        dao.saveAsync(r);
        if (r.getOperatorRefs() != null) {
            for (String ref: r.getOperatorRefs()) {
                dao.removeAsync(ref, Operator.class, "chatRoomRefs", roomRef);
            }
        }
        
        if (r.getGuestRef() != null) {
            dao.deleteValueAsync(r.getGuestRef(), Guest.class, "chatRoomRef");
        }
    }
  
    /**
     * Get ChatRoom
     */
    public ChatRoom getChatRoom(String roomRef) {
        return dao.get(roomRef); 
    }

}
