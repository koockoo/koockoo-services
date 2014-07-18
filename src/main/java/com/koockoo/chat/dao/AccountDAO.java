package com.koockoo.chat.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.koockoo.chat.model.ChatAccount;

@Repository
public class AccountDAO extends BaseCassandraDAO {

	public ChatAccount get(String id) {
		return get(ChatAccount.class, id);
	}
	
	public ChatAccount getByOwnerRef(String ownerRef) {
	    Statement stmt = buildQueryForColumn(ChatAccount.class, "ownerRef", ownerRef);
        if (stmt==null) return null;

        List<ChatAccount> result = sf.getMappingSession().getByQuery(ChatAccount.class, stmt);
        if (result == null || result.size()==0) return null;

        return result.get(0);
	}

}
