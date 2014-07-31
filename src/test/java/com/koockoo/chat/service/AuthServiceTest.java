package com.koockoo.chat.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.mapping.EntityTypeParser;
import com.koockoo.chat.Main;
import com.koockoo.chat.model.Auth;
import com.koockoo.chat.model.Credentials;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class, loader = SpringApplicationContextLoader.class)
public class AuthServiceTest {
	
	@Autowired
	AuthService target;
	
	@BeforeClass 
	public static void init() { 
		EntityTypeParser.remove(Auth.class);
	}	

	@Test
	public void testOperatorAuth() throws Exception {
		Auth auth = target.operatorSignin("test_login", "test_pwd");
		Assert.assertNull(auth);
		
		//create cred
		Credentials cred = target.createCredentials("test_login", "test_pwd", "test_name");
		Assert.assertNotNull(cred);
		
		//signin
		auth = target.operatorSignin(cred.getLogin(), cred.getPassword());
		Assert.assertNotNull(auth);
		
		//signout
		target.signout(auth.getId());		
		
		// delete cred
		target.deleteCredentials(cred.getLogin());
		
		// try sign in
		auth = target.operatorSignin(cred.getLogin(), cred.getPassword());
		Assert.assertNull(auth);
	}
	
	@Test
	public void testGuestAuth() throws Exception {
		Auth auth = target.guestSignin("test_name");
		Assert.assertNotNull(auth);
		
		//signout
		target.signout(auth.getId());		
				
		// try sign in
		Assert.assertFalse(target.authenticate(auth.getId()));
	}
}
