package com.koockoo.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.CommonDAO;
import com.koockoo.chat.model.db.Auth;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.db.Message;
import com.koockoo.chat.model.db.Operator;
import com.koockoo.chat.model.ui.AuthUI;
import com.koockoo.chat.model.ui.ChatRoomUI;
import com.koockoo.chat.model.ui.GuestUI;
import com.koockoo.chat.model.ui.MessageUI;
import com.koockoo.chat.model.ui.OperatorUI;
import com.koockoo.chat.util.DateUtil;

/***
 * Convert DB models into UI and back
 */
@Service
public class ModelConvertor {
    
    private static final Logger log = Logger.getLogger(ModelConvertor.class.getName());
    
    
    @Autowired
    CommonDAO dao;

    public List<ChatRoomUI> chatRoomsToUI(List<ChatRoom> rooms) {
        if (rooms == null) return null;
        List<ChatRoomUI> uiRooms = new ArrayList<>();
        for (ChatRoom r: rooms) {
            uiRooms.add(chatRoomToUI(r));
        }
        return uiRooms;
    }
    
    public ChatRoomUI chatRoomToUI(ChatRoom e) {
        Guest g = dao.get(Guest.class, e.getGuestRef());
        return chatRoomToUI(e, g);
    }
    
    public ChatRoomUI chatRoomToUI(ChatRoom e, Guest g) {
        ChatRoomUI c = new ChatRoomUI();
        c.setId(e.getId());
        c.setGuest(guestToUI(g));
        c.setState(e.getState().name());
        return c;
    }

    public GuestUI guestToUI(Guest e) {
        GuestUI g = new GuestUI();
        g.setId(e.getId());
        g.setDisplayName(e.getDisplayName());
        g.setLocation(e.getLocation());
        g.setEnv(e.getEnv());
        return g;
    }

    public OperatorUI operatorToUI(Operator e) {
        OperatorUI o = new OperatorUI();
        o.setEmail(e.getEmail());
        o.setTopicRef(e.getTopicRef());
        o.setId(e.getId());
        o.setDisplayName(e.getDisplayName());
        return o;
    }
    
    public AuthUI authToUI(Auth e) {
        AuthUI a = new AuthUI();
        a.setId(e.getId());
        a.setTopicRef(e.getTopicRef());
        
        if (e.getOperatorRef() != null) {
            Operator o = dao.get(Operator.class, e.getOperatorRef());
            a.setOperator(operatorToUI(o));
        }

        if (e.getGuestRef() != null) {
            Guest g = dao.get(Guest.class, e.getGuestRef());
            a.setGuest(guestToUI(g));
        }
        return a;
    }
   
    public List<MessageUI> messagesToUI(List<Message> ms) {
        if (ms == null) return null;
        List<MessageUI> result = new ArrayList<>();
        for (Message m: ms) {
            result.add(messageToUI(m));
        }
        return result;
    }
    
    public MessageUI messageToUI(Message e) {
        String utcDate = DateUtil.timeUUID2ISOString(e.getKey().getId());
        MessageUI m = new MessageUI();
        m.setId(e.getKey().getId().toString());
        m.setChatRoomId(e.getKey().getChatRoomId());
        m.setAuthorRef(e.getAuthorRef());
        m.setAuthorType(e.getAuthorType());
        m.setText(e.getText());
        m.setUtcDateTime(utcDate);
        String name = "";
        if (e.getAuthorType() == 0) {
            name  = dao.get(Guest.class, e.getAuthorRef()).getDisplayName();
        } else {
            name  = dao.get(Operator.class, e.getAuthorRef()).getDisplayName();
        }
        m.setAuthorName(name);
        return m;
    }
}
