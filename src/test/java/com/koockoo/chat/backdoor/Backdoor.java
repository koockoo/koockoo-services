package com.koockoo.chat.backdoor;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingSession;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Guest;

public class Backdoor {

    static Cluster cluster;
    static Session session;
    static String keyspace = "koockoo";
    static MappingSession m;
    
    public static void main(String[] args) {
        connect();
        m = new MappingSession(keyspace, session);
        openChatRoom();
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
        Guest g = new Guest();
        g.setDisplayName(displayName);
        m.save(g);

        ChatRoom r = new ChatRoom();
        r.setGuestRef(g.getId());
        r.setTopicRef(topic);
        m.save(r);       
        System.out.println("chatroom opened");
    }

}
