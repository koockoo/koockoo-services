package com.koockoo.chat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.koockoo.chat.model.db.ChatRoom;

@Repository
public class ChatRoomDAO extends BaseCassandraDAO {

    public ChatRoom get(String id) {
        return get(ChatRoom.class, id);
    }
    
    public List<ChatRoom> getPendingByTopic(String topicRef) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("topicRef", topicRef);
        m.put("state", ChatRoom.States.PENDING.name());
        Statement stmt = buildQueryForColumns(ChatRoom.class, m);
        return getByQuery(ChatRoom.class, stmt);
    }
}
