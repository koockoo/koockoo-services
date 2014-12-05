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
import com.koockoo.chat.model.db.Message;
import com.koockoo.chat.model.db.Operator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class, loader = SpringApplicationContextLoader.class)
public class MessageServiceTest {

    @Autowired
    MessageService target;
    
    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    AuthService authService;
    
    @Autowired
    AccountService accountService;
    
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
    public void emulateChatRoomConversation() throws Exception {
        // open chat room
        ChatRoom room1 = chatRoomService.openChatRoomByTopic(guestName, account.getTopicRef());
        String guest = room1.getGuestRef(); 
        String oper = account.getOwnerRef();
        
        // get pending chat room
        List<ChatRoom> rooms = chatRoomService.getPendingChatRoomsByOperator(account.getOwnerRef());
        room1 = rooms.get(0);
        
        // accept chat room
        room1 = chatRoomService.acceptChatRoom(room1.getId(), account.getOwnerRef());
                
        // guest publishes a message
        target.publishMessage(guest, 0, room1.getId(), "message1");
        sleep(1000);
        
        List<Message> opMsgs = target.readMessagesByOperator(oper, null);
        Message last = opMsgs.get(opMsgs.size()-1);
        Assert.assertEquals(1, opMsgs.size());
        
        // guest publishes the second message
        target.publishMessage(guest, 0, room1.getId(), "message2");
        sleep(1000);
        
        // no cut-off, operator reads 2 guest messages
        opMsgs = target.readMessagesByOperator(oper, null);
        Assert.assertEquals(2, opMsgs.size());

        // cut-off, operator reads 1 message        
        opMsgs = target.readMessagesByOperator(oper, last.getKey().getId());
        Assert.assertEquals(1, opMsgs.size());
        
        // operator publishes the second message
        target.publishMessage(oper, 1, room1.getId(), "message3");
        sleep(1000);       
        
        // no cut-off, operator reads 2 guest messages        
        opMsgs = target.readMessagesByOperator(oper, null);
        Assert.assertEquals(2, opMsgs.size());
        
        // cut-off, operator reads 1 message    
        opMsgs = target.readMessagesByOperator(oper, last.getKey().getId());
        Assert.assertEquals(1, opMsgs.size());
        
        List<Message> gMsgs = target.readMessagesByGuest(guest, null);
        Assert.assertEquals(1, gMsgs.size());

        gMsgs = target.readMessagesByGuest(guest, last.getKey().getId());
        Assert.assertEquals(1, gMsgs.size());
        
        // guest 2 opens chatroom
        
        ChatRoom room2 = chatRoomService.openChatRoomByTopic(guestName, account.getTopicRef());
        String guest2 = room2.getGuestRef(); 
        
        // get pending chat room
        rooms = chatRoomService.getPendingChatRoomsByOperator(account.getOwnerRef());
        room2 = rooms.get(0);
        
        // accept chat room
        room2 = chatRoomService.acceptChatRoom(room2.getId(), account.getOwnerRef());
        
        // guest2 publishes a message
        target.publishMessage(guest2, 0, room2.getId(), "message4");
        sleep(1000);       
        
        // cut-off, operator reads 2 messages     
        opMsgs = target.readMessagesByOperator(oper, last.getKey().getId());
        Assert.assertEquals(2, opMsgs.size());
        
        // close chat room 
        chatRoomService.closeChatRoom(room1.getId());
        
    }
    
    private void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {}
    }
}
