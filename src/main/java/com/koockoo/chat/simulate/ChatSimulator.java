package com.koockoo.chat.simulate;

import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.CommonDAO;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.service.AccountService;
import com.koockoo.chat.service.ChatRoomService;
import com.koockoo.chat.service.MessageService;

@Service
public class ChatSimulator {

    private static final Logger     log      = Logger.getLogger(ChatSimulator.class.getName());
    private static final String[]   NAMES    = { "Clinet 1", "Clinet 2", "Clinet 3", "Clinet 4", "Николай Степаныч", "Андрей Семеныч", "Вадим" };
    private static final ChatRoom[] ROOMS    = new ChatRoom[10];
    private static final Random     RANDOM   = new Random();

    @Autowired
    ChatRoomService                 chatRoomService;

    @Autowired
    MessageService                  messageService;
    
    @Autowired
    AccountService accountService;

    @Autowired
    CommonDAO                       dao;

    public void startTalkingToGuest(String chatRoomId, String operatorRef) {
        log.info("talk to guest simulator started");
        chatRoomService.acceptChatRoom(chatRoomId, operatorRef);
        for (int i=0; i<10; i++) {
            messageService.publishMessage(operatorRef, 1, chatRoomId, "some text "+ i);
            sleepMe(1000);
        }
        log.info("talk to guest simulator completed");
    }
    
    public void startTalkingToOperator(String topicRef) {
        log.info("guest simulator started");
        openChatRooms(topicRef);
        postMessages();
        log.info("guest simulator completed");
    }
    
    public void openChatRooms(String topicRef) {

        for (int i = 0; i < ROOMS.length; i++) {
            log.info("opening chatroom");
            ChatRoom room = chatRoomService.openChatRoomByTopic(getRandomName(), topicRef);
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
            Thread.sleep(l);
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
