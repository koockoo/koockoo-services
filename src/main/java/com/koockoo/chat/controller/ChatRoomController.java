package com.koockoo.chat.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.ResponseCode;
import com.koockoo.chat.model.ResponseWrapper;
import com.koockoo.chat.model.db.Auth;
import com.koockoo.chat.model.db.ChatRoom;
import com.koockoo.chat.model.ui.ChatRoomUI;
import com.koockoo.chat.service.ChatRoomService;
import com.koockoo.chat.service.ModelConvertor;

@RestController
@RequestMapping(value = "**/chatroom")
public class ChatRoomController {
    
    @Autowired
    ChatRoomService service;
    
    @Autowired
    ModelConvertor convertor;
    
    private static final Logger log = Logger.getLogger(ChatRoomController.class.getName());
    
    /** Guest connects to koockoo and initializes new chatroom */
    @RequestMapping(value = "connect", method = RequestMethod.POST)
    public void createChatroom() {
        
    }
    
    /** operator app retrieves list of pending chatrooms*/
    @RequestMapping(value = "pending", method = RequestMethod.GET)
    public ResponseWrapper<List<ChatRoomUI>> getPendingChatrooms(@RequestParam String token) {
        try {
            log.info("Retrieve peding for token:"+ token);
            List<ChatRoom> pending  = service.getPendingChatRooms(token);
            log.info("Retrieved "+pending.size()+ " peding for token:"+ token);
            return new ResponseWrapper<>(convertor.chatRoomsToUI(pending));
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.INTERNAL_ERROR, e.getMessage());
        }
    }
 
    /** guest opens a new chatroom*/
    @RequestMapping(value = "open", method = RequestMethod.GET)
    public ResponseWrapper<ChatRoomUI> openChatroom(@RequestParam String displayName, @RequestParam String topicRef) {
        try {
            log.info("opening chatroom for topic:"+ topicRef +" by:"+displayName);
            ChatRoom r = service.openChatRoom(displayName, topicRef);
            log.info("chatroom opened for topic:"+ topicRef +" by:"+displayName);
            return new ResponseWrapper<>(convertor.chatRoomToUI(r));
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    /** guest opens a new chatroom*/
    @RequestMapping(value = "close", method = RequestMethod.GET)
    public ResponseWrapper<String> closeChatroom(@RequestParam String roomRef) {
        try {
            log.info("closing chatroom: "+ roomRef);
            service.closeChatRoom(roomRef);
            log.info("chatroom closed: "+ roomRef);
            return new ResponseWrapper<>();
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.INTERNAL_ERROR, e.getMessage());
        }
    } 
    
    /** operator accepts chatroom converstaion */
    /** operator app retrieves list of pending chatrooms*/
    @RequestMapping(value = "accept", method = RequestMethod.GET)
    public ResponseWrapper<ChatRoomUI> acceptChatroom(@RequestParam String roomRef, @RequestParam String operatorId) {
        try {
            log.info("accepting chatroom: "+ roomRef);
            ChatRoom r  = service.acceptChatRoom(roomRef, operatorId);
            log.info("chatroom accepted: "+ roomRef);
            return new ResponseWrapper<>(convertor.chatRoomToUI(r));
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.INTERNAL_ERROR, e.getMessage());
        }
    }

}