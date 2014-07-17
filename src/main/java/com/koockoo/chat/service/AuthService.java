package com.koockoo.chat.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.AuthDAO;
import com.koockoo.chat.dao.CassandraSessionFactory;
import com.koockoo.chat.model.Auth;
import com.koockoo.chat.model.ChatContact;
import com.koockoo.chat.model.ChatOperator;
import com.koockoo.chat.model.Credentials;

@Service
public class AuthService {
	
	private static final Logger log = Logger.getLogger(AuthService.class.getName());
	
	@Autowired
	private AuthDAO authDAO; 
	
	/**
	 * Create Credentials object for the given operator.
	 * @param login 
	 * @param password
	 * @param user
	 * @return Credentials
	 */
	public Credentials createCredentials(String login, String password, String operatorRef)  {
		Credentials cred = new Credentials(login, password);
		cred.setOperatorRef(operatorRef);
		return authDAO.save(cred);
	}

	public void expressRegister(String login, String password, String displayName)  {
		Credentials cred = new Credentials(login, password);
		ChatOperator oper = new ChatOperator();
		oper.setDisplayName(displayName);
		oper.setEmail(login);
		cred.setOperatorRef(login);
		authDAO.register(cred, oper);
	}
	
	public Auth signin(String login, String password) throws Exception {
		Auth auth = null;
		// get credentials object for current user 
		Credentials cred = authDAO.loadCredentials(login);
		if (cred != null && cred.matches(login, password)) {
			// create new auth token
			auth = new Auth();
			auth.setCredentialsRef(cred.getLogin());
			authDAO.save(auth);
		}
		return auth;
	}	

	/**
	 * Verify that Auth is alive
	 * @param token
	 * @return true if valid Auth exists otherwise false
	 */
	public boolean authenticate(String token) {
		return authDAO.extendAuth(token);
	}	
	
	public Auth guestSignin(String displayName) throws Exception {
		// get credentials object for current user 
		Auth auth = new Auth();
		authDAO.save(auth);		
		return auth;
	}	
	
	public void signout() {
		// get credentials object for current user 
		String token = null; //TODO: get token for current session. Token set by security filter
		try {
			authDAO.deleteAuth(token);
		} catch (Exception e) {
			log.info("Skip. Exception thrown on delete Auth:"+token);
		}
	}	
}
