package com.koockoo.chat.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.AccountDAO;
import com.koockoo.chat.model.ChatAccount;

@Service
public class AccountService {
	private static final Logger log = Logger.getLogger(AccountService.class.getName());
	
	@Autowired
	private AccountDAO accountDao;
	
	@Autowired
	private AuthService authService;
	
	public ChatAccount save(ChatAccount account) {
		return accountDao.save(account);
	}

	/**
	 * Create credentials, Operator and Account
	 * @param email
	 * @param password
	 * @param displayName
	 * @return newly created Account
	 */
	public ChatAccount expressRegister(String email, String password, String displayName) {
		ChatAccount acc = getByOwnerEmail(email);
		if (acc != null)	{
			throw new IllegalArgumentException("This email already assosiated with one of koockoo accounts");
		}
		authService.expressRegister(email, password, displayName);
		acc = new ChatAccount();
		acc.setOwnerRef(email);
		return save(acc);
	}
	
	public String generateSnippet(String ownerEmail) {
		log.info("in generateSnippet for email:"+ownerEmail);
		ChatAccount acc = getByOwnerEmail(ownerEmail);
		if (acc == null)	{
			throw new IllegalArgumentException("This email is not assosiated with any of koockoo accounts");
		}	
		return buildSnippet(acc.getId());
	}
	
	public ChatAccount getById(String accountId) {
		return accountDao.get(accountId);
	}

	public ChatAccount getByOwnerEmail(String email) {
		return accountDao.getByOwnerRef(email);
	}	
	
	private String buildSnippet(String accountId) {
		return 
			"<script type='text/javascript' src='http://koockoo.com/client/js/koockoo.js'/>\n"+
			"<script type='text/javascript'>\n"+
			"    var _koockoo = {'id':'"+accountId+"'};\n"+
			"</script>";
	}
}
