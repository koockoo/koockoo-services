package com.koockoo.chat.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.Auth;
import com.koockoo.chat.model.ResponseWrapper;
import com.koockoo.chat.model.ui.ChatRoom;
import com.koockoo.chat.service.ChatRoomService;

@RestController
@RequestMapping(value = "**/chatroom")
public class ChatRoomController {
    
    @Autowired
    ChatRoomService service;
    
    private static final Logger log = Logger.getLogger(ChatRoomController.class.getName());
    
    /** Operator Signin with credentials */
//    @RequestMapping(value = "signin/operator", method = RequestMethod.POST)
//    public ResponseWrapper<Auth> operatorSignin(@RequestParam String login, @RequestParam String password) {
//        
//    }
    
    /** Guest connects to koockoo and initializes new chatroom */
    @RequestMapping(value = "connect", method = RequestMethod.POST)
    public void createChatroom() {
        
    }
    
    /** operator app retrieves list of pending chatrooms*/
    @RequestMapping(value = "pending", method = RequestMethod.GET)
    public ResponseWrapper<List<ChatRoom>> getPendingChatrooms() {
        List<ChatRoom> pending  = service.getPendingChatRooms("");
        return new ResponseWrapper<>(pending);
    }
    
    /** operator accepts chatroom converstaion */
    public void acceptChatroom() {
        
    }
}
