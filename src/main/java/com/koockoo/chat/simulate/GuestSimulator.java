package com.koockoo.chat.simulate;

import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.CommonDAO;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.service.ChatRoomService;
import com.koockoo.chat.service.MessageService;

@Service
public class GuestSimulator extends Thread {

    private static final Logger     log      = Logger.getLogger(GuestSimulator.class.getName());
    private static final String[]   NAMES    = { "Clinet 1", "Clinet 2", "Clinet 3", "Clinet 4", "Николай Степаныч", "Андрей Семеныч", "Вадим" };
    private static final ChatRoom[] ROOMS    = new ChatRoom[10];
    private static final Random     RANDOM   = new Random();

    @Autowired
    ChatRoomService                 chatRoomService;

    @Autowired
    MessageService                  messageService;

    @Autowired
    CommonDAO                       dao;

    private String topicRef = "50d96f99-16c4-4ecf-a3bb-3a98b008e817";

    public GuestSimulator() {
    }

    public GuestSimulator(String topicRef) {
        this.topicRef = topicRef;
    }

    public void begin(String topicRef) {
        GuestSimulator gs = new GuestSimulator(topicRef);
        gs.chatRoomService = chatRoomService;
        gs.messageService = messageService;
        gs.dao = dao;
        gs.start();
    }

    public void run() {
        log.info("guest simulator started in new thread");
        openChatRooms();
        postMessages();
        log.info("guest simulator completed");
    }

    public void openChatRooms() {

        for (int i = 0; i < ROOMS.length; i++) {
            log.info("opening chatroom");
            ChatRoom room = chatRoomService.openChatRoom(getRandomName(), topicRef);
            log.info("chatroom is opened: " + room.getId());
            ROOMS[i] = room;
            postMessages();
        }
    }

    ChatRoom getRandomRoom() {
        
        ChatRoom room = null;

        do {
            sleepMe(2000);
            int i = ROOMS.length - RANDOM.nextInt(ROOMS.length);
            for (; i >= 0; i--) {
                try {
                    room = ROOMS[i];
                } catch (Exception e) {}
                if (room != null) {
                    room = dao.get(ChatRoom.class, room.getId());
                    ROOMS[i] = room;
                    if (room.getState().equals(ChatRoom.States.ACTIVE)) {
                        log.info("room selected: "+ room.getId());
                        return room;
                    } else {
                        room = null;
                    }
                }
            }
        } while (true);
    }

    public void postMessages() {
        for (int j = 0; j < 5; j++) {
            publishMessage(getRandomRoom(), j);
            sleepMe(100);
        }
    }

    /** Build and save a Message asynchronously */
    public void publishMessage(ChatRoom room, int j) {
        messageService.publishMessage(room.getGuestRef(), 0, room.getId(), "some text "+ j);
        log.info("posted message to chatroom:" + room.getId());
    }

    private void sleepMe(long l) {
        try {
            sleep(l);
        } catch (InterruptedException e) {
        }
    }

    private String getRandomName() {
        int i = NAMES.length - RANDOM.nextInt(NAMES.length);
        if (i >= NAMES.length) {
            i = NAMES.length - 1;
        } else if (i < 0) {
            i = 0;
        }
        return NAMES[i];
    }

}
