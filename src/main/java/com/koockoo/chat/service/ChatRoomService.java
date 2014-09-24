package com.koockoo.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.koockoo.chat.model.ui.ChatRoom;
import com.koockoo.chat.model.ui.Guest;
import com.koockoo.chat.util.DateUtil;

@Service
public class ChatRoomService {
    
    /** retrieve all pending conversations by operator */
    public List<ChatRoom> getPendingChatRooms(String operatorRef) {
        return pendingMock();
    }
    
    private List<ChatRoom> pendingMock() {
        List<ChatRoom> l = new ArrayList<>();
        Guest g = new Guest();
        g.setDisplayName("Bill Mill");
        g.setEnv("Boston, USA");
        g.setId(UUID.randomUUID().toString());
        g.setConnectionDate(DateUtil.formatDateTime());
        ChatRoom c = new ChatRoom();
        c.setGuest(g);
        l.add(c);

        g = new Guest();
        g.setDisplayName("Jim Bim");
        g.setEnv("Moscow, Ru");
        g.setId(UUID.randomUUID().toString());
        g.setConnectionDate(DateUtil.formatDateTime());
        c = new ChatRoom();
        c.setGuest(g);
        l.add(c);

        return l;
    }
}
