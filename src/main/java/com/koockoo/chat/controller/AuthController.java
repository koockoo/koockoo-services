package com.koockoo.chat.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.Auth;
import com.koockoo.chat.model.ResponseCode;
import com.koockoo.chat.model.ResponseWrapper;
import com.koockoo.chat.service.AuthService;

@RestController
@RequestMapping(value = "**/auth")
public class AuthController {
	
    private static final Logger log = Logger.getLogger(AuthController.class.getName());
    
	@Autowired
	AuthService authService;
	
	/** Operator Signin with credentials */
	@RequestMapping(value = "signin/operator", method = RequestMethod.POST)
	public ResponseWrapper<Auth> operatorSignin(@RequestParam String login, @RequestParam String password) {
	    log.info("operator login attempt by : "+login);
		Auth auth = null;
		try {
			auth = authService.operatorSignin(login, password); 
		} catch (Exception e) {
		    log.info("operator login unexpected error : "+login);
			return new ResponseWrapper<Auth>(ResponseCode.INTERNAL_ERROR, e.getMessage());
		}
		if (auth == null) {
		    log.info("operator login invalid by : "+login);
			return new ResponseWrapper<Auth>(ResponseCode.INVALID_CREDENTIALS, "Invalid email or password"); 
		}
		log.info("operator login success by : "+login);
		return new ResponseWrapper<Auth>(auth);
	}
	
	/** Guest Signin with name */
	@RequestMapping(value = "signin/guest", method = RequestMethod.POST)
	public ResponseWrapper<Auth> guestSignin(@RequestParam String displayName) {
		Auth auth = null;
		try {
			auth = authService.guestSignin(displayName); 
		} catch (Exception e) {
			return new ResponseWrapper<Auth>(ResponseCode.INTERNAL_ERROR, e.getMessage());
		}
		return new ResponseWrapper<Auth>(auth);
	}	
	
	/** Signout. Invalidates auth */
	@RequestMapping(value = "signout", method = RequestMethod.POST)
	public ResponseWrapper<String> signout(@RequestParam String authId) {
		try {
			authService.signout(authId);
		} catch (Exception e) {
			// ignore
		}
		return new ResponseWrapper<>();
	}	
		
	/** Send user/pwd on email */
	@RequestMapping(value = "recover", method = RequestMethod.POST)
	public ResponseWrapper<String> recover(@RequestParam String email) {
		// TODO: implement
		return new ResponseWrapper<>();
	}	
	
	/** Unregister remove email/pwd Credentials */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ResponseWrapper<String> delete() {
		// TODO: implement
		return new ResponseWrapper<>();
	}	
}
