package com.koockoo.chat.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.mapping.schemasync.SchemaSync;
import com.koockoo.chat.Main;
import com.koockoo.chat.dao.CassandraSessionFactory;
import com.koockoo.chat.dao.CommonDAO;
import com.koockoo.chat.model.Credentials;
import com.koockoo.chat.model.db.Account;
import com.koockoo.chat.model.db.Auth;
import com.koockoo.chat.model.db.Operator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class, loader = SpringApplicationContextLoader.class)
public class AccountServiceTest {

    @Autowired
    AccountService target;

    @Autowired
    AuthService    authService;
    
    @Autowired
    CommonDAO dao;

    public void init() {
        CassandraSessionFactory sf = dao.getSessionFactory();
        SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Account.class);
        SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Operator.class);
        SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Credentials.class);
        SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Auth.class);
    }

    @Test
    public void testExpress() throws Exception {
        init();
        try {
            target.delete("email@123", "password123");
        } catch (Exception e) {
        }

        Account acc = target.expressRegister("email@123", "password123", "displayName123");
        Assert.assertNotNull(acc);

        acc = target.getByOwnerEmail("email@123");
        Assert.assertNotNull(acc);

        try {
            acc = target.expressRegister("email@123", "password123", "displayName123");
            Assert.assertNull(acc);
        } catch (Exception e) {
        }

        target.delete("email@123", "password123");

        acc = target.getByOwnerEmail("email@123");
        Assert.assertNull(acc);
    }
}
