package com.koockoo.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.CommonDAO;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.ui.ChatRoomUI;
import com.koockoo.chat.model.ui.GuestUI;

/***
 * Convert DB models into UI and back
 */
@Service
public class ModelConvertor {
    
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
        g.setDisplayName(e.getDisplayName());
        g.setLocation(e.getLocation());
        g.setEnv(e.getEnv());
        return g;
    }
}
