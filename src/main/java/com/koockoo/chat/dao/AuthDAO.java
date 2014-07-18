package com.koockoo.chat.dao;

import org.springframework.stereotype.Repository;

import com.koockoo.chat.model.Auth;
import com.koockoo.chat.model.ChatOperator;
import com.koockoo.chat.model.Credentials;

@Repository
public class AuthDAO extends BaseCassandraDAO {
	
	public Credentials saveCredentials(String login, String password) throws Exception {
		Credentials cred = new Credentials(login, password);
		return save(cred);
	}

	public void register(Credentials cred, ChatOperator oper) {
		sf.getMappingSession().withBatch().save(cred).save(oper).execute();
	}


	public void deleteAuth(Auth auth) {
		delete(auth);
	} 

	public void deleteAuth(String token) {
		delete(Auth.class, token);
	} 

	public Credentials getCredentials(String login) throws Exception {
		return get(Credentials.class, login);
	}
	
	public Auth loadAuth(String token) throws Exception {
		Auth auth = get(Auth.class, token);
		auth.setCredentials(loadCredentials(auth.getCredentialsRef()));
		return auth;
	}

	/**
	 * Extend live for valid auth.
	 * @param token
	 * @return true if auth was updated successfully otherwise false
	 */
	public boolean extendAuth(String token)  {
		Auth auth = get(Auth.class, token);
		if (auth != null) {
			save(auth);
			return true;
		}
		return false;
	} 
	
	public Credentials loadCredentials(String login) throws Exception {
		Credentials cred = get(Credentials.class, login);
		if (cred.getOperatorRef() != null) {
			ChatOperator cont = get(ChatOperator.class, cred.getOperatorRef());
			cred.setChatOperator(cont);
		}
		
		return cred;
	}	
		
}
