package com.koockoo.chat.service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.MessageDAO;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.db.Message;
import com.koockoo.chat.model.db.MessageKey;
import com.koockoo.chat.model.db.Operator;

@Service
public class MessageService {

    @Autowired
    MessageDAO dao;

    /** Build and save a Message asynchronously */
    public Message publishMessage(String authorRef, int authorType, String chatRoomId, String text) {
        MessageKey key = new MessageKey();
        key.setChatRoomId(chatRoomId);
        Message m = new Message();
        m.setKey(key);
        m.setText(text);
        m.setAuthorRef(authorRef);
        m.setAuthorType(authorType);
        dao.saveAsync(m);
        dao.updateValueAsync(chatRoomId, ChatRoom.class, "id", chatRoomId); // to keep alive (refresh TTL)
        return m;
    }

 
    /** Read messages from a chatroom */
    public List<Message> readMessages(UUID lastMsgId, String chatRoomId) {
        return readMessages(lastMsgId, chatRoomId, null);
    }

    /** Read messages from a chatroom, excluding author */
    public List<Message> readMessages(UUID lastMsgId, String chatRoomId, String excludeAuthor) {
        String[] chatRooms = {chatRoomId};
        return readMessages(lastMsgId, chatRooms, excludeAuthor);
    }
 
    /** Read messages from chatrooms  */
    public List<Message> readMessages(UUID lastMsgId, String[] chatRooms, String excludeAuthor) {
        List<Message> messages = dao.queryUnreadMessages(lastMsgId, chatRooms);
        if (excludeAuthor != null && messages != null) {
            messages = removeAuthorMessages(excludeAuthor, messages);
        }
        return messages;
    }

    /**
     * Operator reads message for all chatrooms.
     */
    public List<Message> readMessagesByOperator(String operatorId, UUID lastMsgId) {
        Operator op = dao.get(Operator.class, operatorId);
        String[] chatRooms = op.getChatRoomRefs().toArray(new String[op.getChatRoomRefs().size()]);
        return readMessages(lastMsgId, chatRooms, operatorId);
    }

    /**
     * Guest reads message for all chatrooms.
     */
    public List<Message> readMessagesByGuest(String guestId, UUID lastMsgId) {
        Guest g = dao.get(Guest.class, guestId);
        return readMessages(lastMsgId, g.getChatRoomRef(), guestId);
    }
    
    private List<Message> removeAuthorMessages(String authorId, List<Message> msgs) {
        for (Iterator<Message> it = msgs.iterator(); it.hasNext();) {
            Message m = it.next();
            if (m.getAuthorRef().equals(authorId)) {
                it.remove();
            }
        }
        return msgs;
    }
}
