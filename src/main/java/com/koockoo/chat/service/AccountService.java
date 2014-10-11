package com.koockoo.chat.service;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.AccountDAO;
import com.koockoo.chat.model.Credentials;
import com.koockoo.chat.model.db.Account;
import com.koockoo.chat.model.db.Operator;

@Service
public class AccountService {
	private static final Logger log = Logger.getLogger(AccountService.class.getName());
	
	@Autowired
	private AccountDAO accountDao;
	
	@Autowired
	private AuthService authService;
	
	public Account save(Account account) {
		return accountDao.save(account);
	}

	/**
	 * Create credentials, Operator and Account
	 * @param email
	 * @param password
	 * @param displayName
	 * @return newly created Account
	 */
	public Account expressRegister(String email, String password, String displayName) {
		Account acc = getByOwnerEmail(email);
		if (acc != null)	{
			throw new IllegalArgumentException("This email already assosiated with one of koockoo accounts");
		}
		String topicRef = UUID.randomUUID().toString();
		Operator op = authService.expressRegister(email, password, displayName, topicRef);
		acc = new Account();
		acc.setOwnerRef(op.getId());
		acc.setTopicRef(topicRef);
		acc.setOwnerEmail(email);
		return save(acc);
	}

    /**
     * Create credentials, Operator and Account
     * @param email
     * @param password
     * @param displayName
     * @return newly created Account
     */
    public void delete(String email, String password) {
        Account acc = getByOwnerEmail(email);
        if (acc == null)    {
            throw new IllegalArgumentException("Not an owner email");
        }
        
        Credentials cred = authService.getCredentials(email, password);
        if (cred == null)    {
            throw new IllegalArgumentException("Invalid Credentials");
        }
        accountDao.delete(acc);
    }
    
	public String generateSnippet(String ownerEmail) {
		log.info("in generateSnippet for email:"+ownerEmail);
		
		Account acc = getByOwnerEmail(ownerEmail);
		if (acc == null)	{
			throw new IllegalArgumentException("This email is not assosiated with any of koockoo accounts");
		}	
		return buildSnippet(acc.getId());
	}
	
	public Account getById(String accountId) {
		return accountDao.get(accountId);
	}

	public Account getByOwnerEmail(String email) {
		return accountDao.getByOwnerEmail(email);
	}	
	
	private String buildSnippet(String accountId) {
		return 
			"<script type='text/javascript' src='http://koockoo.com/client/js/koockoo.js'/>\n"+
			"<script type='text/javascript'>\n"+
			"    var _koockoo = {'id':'"+accountId+"'};\n"+
			"</script>";
	}
}
