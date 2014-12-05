package com.koockoo.chat.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.AuthDAO;
import com.koockoo.chat.model.Credentials;
import com.koockoo.chat.model.db.Auth;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.db.Operator;
import com.koockoo.chat.simulate.GuestSimulator;

@Service
public class AuthService {
	
	private static final Logger log = Logger.getLogger(AuthService.class.getName());
	
	@Autowired
	private AuthDAO dao; 

    @Autowired
    private GuestSimulator guestSimulator; 	
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
		return dao.save(cred);
	}

	public void deleteCredentials(String login)  {
		dao.delete(Credentials.class, login);
	}
	
	public Operator expressRegister(String login, String password, String displayName, String topicRef)  {
		Credentials cred = new Credentials(login, password);
		Operator oper = new Operator();
		oper.setDisplayName(displayName);
		oper.setEmail(login);
		oper.setTopicRef(topicRef);
		cred.setOperatorRef(oper.getId());
		dao.register(cred, oper);
		return oper;
	}
	
	public Auth operatorSignin(String login, String password) {
		Auth auth = null;
		// get credentials object for current user 
		Credentials cred = getCredentials(login, password);
		if (cred != null) {
		    Operator op = dao.get(Operator.class, cred.getOperatorRef());
			// create new auth token
			auth = new Auth();
			auth.setOperatorRef(op.getId());
			auth.setTopicRef(op.getTopicRef());
			auth = dao.save(auth);
		}
		
		// this is for simulating and demo purposes
		if (login.contains("@1") && login.length() == 3) {
		    guestSimulator.begin(auth.getTopicRef());
		}
		return auth;
	}	

    public Credentials getCredentials(String login, String password) {
        Credentials cred = dao.loadCredentials(login);
        if (cred != null && cred.matches(login, password)) {
            return cred;
        }
        return null;
    }
    
	/**
	 * Verify Auth is alive
	 * @param token
	 * @return Auth or null
	 */
	public Auth authenticate(String token) {
		return dao.extendAuth(token);
	}	
	
	/**
	 * Guest does not have login password.
	 * This method creates a guest instance and generates valid auth token to be able to chat.
	 * @param displayName
	 * @return Auth
	 */
	public Auth guestSignin(String displayName, String topicRef) {
		Guest guest = new Guest();
		guest.setDisplayName(displayName);
		guest.setTopicRef(topicRef);
		Auth auth = new Auth();
		auth.setGuestRef(guest.getId());
		auth.setTopicRef(topicRef);

		dao.withBatch()
		   .save(guest)
		   .save(auth)
		   .execute();
		return auth;
	}	
	
	public void signout(String id) {
		try {
			dao.delete(Auth.class, id);
		} catch (Exception e) {
			log.info("Skip. Exception thrown on delete Auth:"+id);
		}
	}	
}
