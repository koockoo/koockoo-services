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
    
    /** operator app retrieves list of pending chatrooms*/
    @RequestMapping(value = "pending", method = RequestMethod.GET)
    public ResponseWrapper<List<ChatRoomUI>> getPendingChatrooms(@RequestParam String token) {
        try {
            log.info("Retrieve peding rooms for token:"+ token);
            List<ChatRoom> pending  = service.getPendingChatRooms(token);
            log.info("Retrieved "+pending.size()+ " peding for token:"+ token);
            return new ResponseWrapper<>(convertor.chatRoomsToUI(pending));
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.INTERNAL_ERROR, e.getMessage());
        }
    }
    
    /** operator app retrieves list of active chatrooms*/
    @RequestMapping(value = "active", method = RequestMethod.GET)
    public ResponseWrapper<List<ChatRoomUI>> getActiveChatrooms(@RequestParam String token) {
        try {
            log.info("Retrieve active rooms for token:"+ token);
            List<ChatRoom> active  = service.getActiveChatRooms(token);
            log.info("Retrieved "+active.size()+ " active for token:"+ token);
            return new ResponseWrapper<>(convertor.chatRoomsToUI(active));
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
    @RequestMapping(value = "accept", method = RequestMethod.POST)
    public ResponseWrapper<ChatRoomUI> acceptChatroom(@RequestParam String roomId, @RequestParam String operatorId) {
        try {
            log.info("accepting chatroom: "+ roomId);
            ChatRoom r  = service.acceptChatRoom(roomId, operatorId);
            log.info("chatroom accepted: "+ roomId);
            return new ResponseWrapper<>(convertor.chatRoomToUI(r));
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    /** to check service availability */
    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public ResponseWrapper<String> ping() {
        return new ResponseWrapper<>();
    }
}
