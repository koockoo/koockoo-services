package com.koockoo.chat.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.gt;
import static com.datastax.driver.core.querybuilder.QueryBuilder.in;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.EntityTypeParser;
import com.datastax.driver.mapping.meta.EntityFieldMetaData;
import com.datastax.driver.mapping.meta.EntityTypeMetadata;
import com.koockoo.chat.model.db.Message;

@Repository
public class MessageDAO extends BaseCassandraDAO {

    public List<Message> queryUnreadMessages(UUID lastMsgId, String[] chatrooms) {
        Statement s = buildQueryForMessages(lastMsgId, chatrooms);
        List<Message> list = getByQuery(Message.class, s);
        return list;
    }
    
    /** Retrieve all messages which timeuuid greater than given lastMsgId*/
    protected <T> Statement buildQueryForMessages(UUID lastMsgId, Object[] chatrooms) {
        EntityTypeMetadata emeta = EntityTypeParser.getEntityMetadata(Message.class);
        EntityFieldMetaData fid = emeta.getFieldMetadata("id");
        EntityFieldMetaData fun = emeta.getFieldMetadata("chatRoomId");
        if (fid != null && fun != null) {
            Select select = QueryBuilder.select().all().from(sf.getKeyspace(), emeta.getTableName());
            select.where(in(fun.getColumnName(), chatrooms));
            if (lastMsgId != null) {
                return select.where(gt(fid.getColumnName(), lastMsgId));
            }
            return select;
        }
        return null;
    }   
}
