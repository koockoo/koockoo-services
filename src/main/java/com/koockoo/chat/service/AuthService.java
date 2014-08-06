package com.koockoo.chat.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.AuthDAO;
import com.koockoo.chat.model.Auth;
import com.koockoo.chat.model.ChatGuest;
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

	public void deleteCredentials(String login)  {
		authDAO.delete(Credentials.class, login);
	}
	
	public void expressRegister(String login, String password, String displayName)  {
		Credentials cred = new Credentials(login, password);
		ChatOperator oper = new ChatOperator();
		oper.setDisplayName(displayName);
		oper.setEmail(login);
		cred.setOperatorRef(login);
		authDAO.register(cred, oper);
	}
	
	public Auth operatorSignin(String login, String password) {
		Auth auth = null;
		// get credentials object for current user 
		Credentials cred = getCredentials(login, password);
		if (cred != null) {
			// create new auth token
			auth = new Auth();
			auth.setOperatorRef(cred.getOperatorRef());
			auth = authDAO.save(auth);
		}
		return auth;
	}	

    public Credentials getCredentials(String login, String password) {
        Credentials cred = authDAO.loadCredentials(login);
        if (cred != null && cred.matches(login, password)) {
            return cred;
        }
        return null;
    }
    
	/**
	 * Verify that Auth is alive
	 * @param token
	 * @return true if valid Auth exists otherwise false
	 */
	public boolean authenticate(String token) {
		return authDAO.extendAuth(token);
	}	
	
	/**
	 * Guest does not have login password.
	 * This method creates a guest instance and generates valid auth token to be able to chat.
	 * @param displayName
	 * @return Auth
	 */
	public Auth guestSignin(String displayName) {
		ChatGuest guest = new ChatGuest();
		guest.setDisplayName(displayName);
		authDAO.save(guest);
		Auth auth = new Auth();
		auth.setGuestRef(guest.getId());
		authDAO.save(auth);		
		return auth;
	}	
	
	public void signout(String id) {
		try {
			authDAO.delete(Auth.class, id);
		} catch (Exception e) {
			log.info("Skip. Exception thrown on delete Auth:"+id);
		}
	}	
}
