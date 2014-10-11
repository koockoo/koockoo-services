package com.koockoo.chat.dao;

import org.springframework.stereotype.Repository;

import com.koockoo.chat.model.Credentials;
import com.koockoo.chat.model.db.Auth;
import com.koockoo.chat.model.db.Operator;

@Repository
public class AuthDAO extends BaseCassandraDAO {
	
	public Credentials saveCredentials(String login, String password) throws Exception {
		Credentials cred = new Credentials(login, password);
		return save(cred);
	}

	public void register(Credentials cred, Operator oper) {
		withBatch().save(cred).save(oper).execute();
	}

	public Credentials getCredentials(String login) throws Exception {
		return get(Credentials.class, login);
	}

	/**
	 * Extend live for valid auth.
	 * @param token
	 * @return true if auth was updated successfully otherwise false
	 */
	public Auth extendAuth(String token)  {
		Auth auth = get(Auth.class, token);
		if (auth != null) {
			return save(auth);
		}
		return null;
	} 
	
	public Credentials loadCredentials(String login)  {
		Credentials cred = get(Credentials.class, login);
		if (cred !=null && cred.getOperatorRef() != null) {
			Operator cont = get(Operator.class, cred.getOperatorRef());
			cred.setChatOperator(cont);
		}
		
		return cred;
	}	
		
}
