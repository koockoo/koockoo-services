package com.koockoo.chat.backdoor;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingSession;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.db.Message;
import com.koockoo.chat.model.db.MessageKey;

public class Backdoor {

    static Cluster cluster;
    static Session session;
    static String keyspace = "koockoo";
    static ChatRoom room;
    static Guest guest; 
    static MappingSession m;
    
    public static void main(String[] args) {
        connect();
        m = new MappingSession(keyspace, session);
        openChatRoom();
        postMessages();
        disconnect();
    }
    
    /** only 1 thread is permitted to open connection */
    static void connect() {
        if (session == null) {
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            session = cluster.connect();
            session.execute("CREATE KEYSPACE IF NOT EXISTS "+ keyspace +
                " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 }");
        }   
    }
    
    static void openChatRoom () {
        System.out.println("opening chatroom");
        String topic = "50d96f99-16c4-4ecf-a3bb-3a98b008e817";
        String displayName = "User 3";
        
        guest = new Guest();
        guest.setDisplayName(displayName);
        m.save(guest);

        room = new ChatRoom();
        room.setGuestRef(guest.getId());
        room.setTopicRef(topic);
        m.save(room);       
        System.out.println("chatroom opened");
    }
    
    static void postMessages() {
        do {
            room = m.get(ChatRoom.class, room.getId());
        } while(room.getState().equals(ChatRoom.States.PENDING));
        
        for (int i = 0; i < 10; i++) {
            publishMessage();
            sleep(10000);
        }
        
    }
    
    /** Build and save a Message asynchronously */
    static void publishMessage() {
        MessageKey key = new MessageKey();
        key.setChatRoomId(room.getId());
        Message ms = new Message();
        ms.setKey(key);
        ms.setText("Message text");
        ms.setAuthorRef(guest.getId());
        ms.setAuthorType(0);
        m.saveAsync(ms);
        System.out.println("posted message to chatroom:"+room.getId());
     }    
    
    static void disconnect() {
        session.close();
        cluster.close();
        System.exit(0);
    }
    
    static void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
        }
    }

}
