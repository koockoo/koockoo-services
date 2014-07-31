package com.koockoo.chat.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.koockoo.chat.Main;
import com.koockoo.chat.model.ChatAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class, loader = SpringApplicationContextLoader.class)
public class AccountServiceTest {

	@Autowired
	AccountService target;

	@Autowired
	AuthService authService;
	
	@Test
	public void testExpress() throws Exception {
		ChatAccount acc = target.expressRegister("email@123", "password123", "displayName123");
		Assert.assertNotNull(acc);
		
		acc = target.getByOwnerEmail("email@123");
		Assert.assertNotNull(acc);
		
		
		
	}
}
