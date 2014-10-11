package com.koockoo.chat.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.koockoo.chat.model.db.Account;
import com.koockoo.chat.model.db.Operator;

@Repository
public class AccountDAO extends BaseCassandraDAO {

	public Account get(String id) {
		return get(Account.class, id);
	}

	public Account load(String id) {
	    Account acc =  get(Account.class, id);
	    Operator op = get(Operator.class, acc.getOwnerRef());
	    acc.setOwner(op);
	    return acc;
	}
	
	public Account getByOwnerEmail(String email) {
	    
	    Statement stmt = buildQueryForColumn(Account.class, "ownerEmail", email);
        if (stmt==null) return null;

        List<Account> result = getByQuery(Account.class, stmt);
        if (result == null || result.size()==0) return null;

        return result.get(0);
	}

}
