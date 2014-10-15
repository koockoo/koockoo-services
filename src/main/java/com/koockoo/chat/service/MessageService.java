package com.koockoo.chat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.MessageDAO;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.db.Message;
import com.koockoo.chat.model.db.MessageKey;
import com.koockoo.chat.model.db.Operator;

@Service
public class MessageService {

    @Autowired
    MessageDAO dao;

    /** Build and save a Message asynchronously */
    public void publishMessage(String authorRef, int authorType, String chatRoomId, String text) {
        MessageKey key = new MessageKey();
        key.setChatRoomId(chatRoomId);
        Message m = new Message();
        m.setKey(key);
        m.setText(text);
        m.setAuthorRef(authorRef);
        m.setAuthorType(authorType);
        dao.saveAsync(m);
    }

    /** Read messages from subscribers queue */
    public List<Message> readMessages(UUID lastMsgId, String[] chatRooms) {
        return dao.queryUnreadMessages(lastMsgId, chatRooms);
    }

    /**
     * Operator reads message for all chatrooms.
     */
    public Map<String, List<Message>> readMessagesByOperator(String operatorId, UUID lastMsgId) {
        Map<String, List<Message>> messages = new HashMap<String, List<Message>>();
        Operator op = dao.get(Operator.class, operatorId);
        String[] chatRooms = op.getChatRoomRefs().toArray(new String[op.getChatRoomRefs().size()]);
        List<Message> all = readMessages(lastMsgId, chatRooms);
        if (all != null) {
            for (Message m : all) {
                String roomId = m.getKey().getChatRoomId();
                List<Message> roomMessages = messages.get(roomId);
                if (roomMessages == null) {
                    roomMessages = new ArrayList<>();
                    messages.put(roomId, roomMessages);
                }
                roomMessages.add(m);
            }
        }
        return messages;
    }

    /**
     * Operator reads message for all chatrooms.
     */
    public List<Message> readMessagesByGuest(String guestId, UUID lastMsgId) {
        Guest g = dao.get(Guest.class, guestId);
        String[] refs = new String[1];
        refs[0] = g.getChatRoomRef();
        return readMessages(lastMsgId, refs);
    }
}
