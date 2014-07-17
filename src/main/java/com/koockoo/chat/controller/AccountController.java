package com.koockoo.chat.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.ChatAccount;
import com.koockoo.chat.model.ResponseCode;
import com.koockoo.chat.model.ResponseWrapper;
import com.koockoo.chat.service.AccountService;

@RestController
@RequestMapping(value = "**/account")
public class AccountController {

	private static final Logger log = Logger.getLogger(AccountController.class.getName());
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/ping", method = RequestMethod.GET) 
    public ResponseWrapper<String> ping() {		
		 return new ResponseWrapper<>();
	}	
	
	@RequestMapping(value = "express", method = RequestMethod.POST) 
    public void createAccount(@RequestParam String email, @RequestParam String password, @RequestParam String displayName) {
		log.info("express register account for email:"+ email);
		ChatAccount account = new ChatAccount();
		accountService.save(account);
	}	
	
	@RequestMapping(value = "snippet/{email}", method = RequestMethod.GET) 
    public ResponseWrapper<String> getSnippet(@PathVariable String email) {
		// get account by email
		ChatAccount account = accountService.getByOwnerEmail(email);
		// create new account from the request.
		if (account != null) {
			return new ResponseWrapper<String>(generateSnippet(account.getId()));
		} else {
			return new ResponseWrapper<String>(ResponseCode.BAD_REQUEST, "No Account Found for "+email);
		}
	}	
	
	private String generateSnippet(String accountId) {
		return 
			"<script type='text/javascript' src='http://koockoo.com/client/js/koockoo.js'/>\n"+
			"<script type='text/javascript'>\n"+
			"    var _koockoo = {'id':'"+accountId+"'};\n"+
			"</script>";
	}
}
