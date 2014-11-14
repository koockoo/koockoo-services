package com.koockoo.chat.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Operator;

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
    
    public List<ChatRoom> getActiveByOperator(Operator oper) {
        List<ChatRoom> rooms = new ArrayList<>();
        List<String> obsolete = new ArrayList<>();
        for (String id: oper.getChatRoomRefs()) {
            ChatRoom r = get(id);
            if (r != null && ChatRoom.States.ACTIVE.equals(r.getState())) {
                rooms.add(r);
            } else {
                obsolete.add(id);
            }
        }
        if (obsolete.size()>0) {
            removeAsync(oper.getId(), Operator.class, "chatRoomRefs", obsolete);
        }
        return rooms;
    }
}
