package com.koockoo.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koockoo.chat.dao.AccountDAO;
import com.koockoo.chat.model.ChatAccount;

@Service
public class AccountService {
	
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
		// create operator
		authService.expressRegister(email, password, displayName);
		ChatAccount acc = new ChatAccount();
		acc.setOwnerRef(email);
		return save(acc);
	}
	
	public ChatAccount getById(String accountId) {
		return accountDao.get(accountId);
	}

	public ChatAccount getByOwnerEmail(String email) {
		return accountDao.getByOwnerRef(email);
	}	
}
