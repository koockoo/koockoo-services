package com.koockoo.chat.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.koockoo.chat.Main;
import com.koockoo.chat.model.Credentials;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class, loader = SpringApplicationContextLoader.class)
public class AuthServiceTest {
	
	@Autowired
	AuthService target;
	
	@Test
	public void testCreateCredentials() throws Exception {
		Credentials cred = target.createCredentials("test", "test", "test");
		Assert.assertNotNull(cred);
	}

}
