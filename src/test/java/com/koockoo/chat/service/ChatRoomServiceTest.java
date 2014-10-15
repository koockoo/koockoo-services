package com.koockoo.chat.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.db.Guest;
import com.koockoo.chat.model.db.Operator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class, loader = SpringApplicationContextLoader.class)
public class ChatRoomServiceTest {

    @Autowired
    ChatRoomService target;
    
    @Autowired
    AccountService accountService;

    @Autowired
    AuthService authService;
    
    @Autowired
    CommonDAO dao;
    
    private String operatorEmail = "koockoo@gmail.com";
    private String operatorName = "koockoo test";
    private String operatorPwd = "koockoo";
    private String guestName = "koockoo guest"; 
    private Account account;

    @Before
    public void init() {
        try {
            CassandraSessionFactory sf = dao.getSessionFactory();
            SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Account.class);
            SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Operator.class);
            SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Credentials.class);
            SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Auth.class);
            SchemaSync.drop(sf.getKeyspace(), sf.getSession(), ChatRoom.class);
            SchemaSync.drop(sf.getKeyspace(), sf.getSession(), Guest.class);
        } catch (Exception e) {}
        
        account = accountService.expressRegister(operatorEmail, operatorPwd, operatorName);
    }
    
    @Test
    public void emulateChatRoomLifecicle() throws Exception {
        // open chat room
        ChatRoom room1 = target.openChatRoom(guestName, account.getTopicRef());
        ChatRoom room2 = target.openChatRoom(guestName, account.getTopicRef());
        Assert.assertEquals(room1.getState(), ChatRoom.States.PENDING);
        Assert.assertEquals(room2.getState(), ChatRoom.States.PENDING);
        
        // get pending chat room
        List<ChatRoom> rooms = target.getPendingChatRoomsByOperator(account.getOwnerRef());
        Assert.assertEquals(2, rooms.size());
        room1 = rooms.get(0);
        
        // accept chat room
        room1 = target.acceptChatRoom(room1.getId(), account.getOwnerRef());
        Assert.assertEquals(room1.getState(), ChatRoom.States.ACTIVE);
        Assert.assertEquals(room1.getOperatorRefs().get(0), account.getOwnerRef());
        
        // close chat room 
        target.closeChatRoom(room1.getId());
        
        room1 = target.getChatRoom(room1.getId());
        Assert.assertEquals(room1.getState(), ChatRoom.States.CLOSED);
        
        rooms = target.getPendingChatRoomsByOperator(account.getOwnerRef());
        Assert.assertEquals(1, rooms.size());
        Assert.assertEquals(rooms.get(0).getState(), ChatRoom.States.PENDING);
    }
}
