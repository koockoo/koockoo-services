package com.koockoo.chat.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.ResponseCode;
import com.koockoo.chat.model.ResponseWrapper;
import com.koockoo.chat.service.AccountService;

@RestController
@RequestMapping(value = "**/account")
public class AccountController {

	private static final Logger log = Logger.getLogger(AccountController.class.getName());
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "ping", method = RequestMethod.GET) 
    public ResponseWrapper<String> ping() {		
		 return new ResponseWrapper<>();
	}	
	
	@RequestMapping(value = "express", method = RequestMethod.POST) 
    public ResponseWrapper<String> express(@RequestParam String email, @RequestParam String password, @RequestParam String displayName) {
		log.info("in express register account for email:"+ email);
		try {
			accountService.expressRegister(email, password, displayName);
		}catch (Exception e) {
			return new ResponseWrapper<String>(ResponseCode.BAD_REQUEST, e.getMessage());
		}
		return new ResponseWrapper<>();
	}	
	
	@RequestMapping(value = "snippet", method = RequestMethod.GET) 
    public ResponseWrapper<String> getSnippet(@RequestParam String email) {
		try {
			log.info("in getSnippet: request param email:"+email);
			return new ResponseWrapper<String>(accountService.generateSnippet(email));
		} catch (Exception e) {
			return new ResponseWrapper<String>(ResponseCode.BAD_REQUEST, e.getMessage());
		}
	}	
	
}
