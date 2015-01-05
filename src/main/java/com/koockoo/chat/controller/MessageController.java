package com.koockoo.chat.controller;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.ResponseCode;
import com.koockoo.chat.model.ResponseWrapper;
import com.koockoo.chat.model.db.Message;
import com.koockoo.chat.model.ui.MessageUI;
import com.koockoo.chat.service.MessageService;
import com.koockoo.chat.service.ModelConvertor;

/**
 * @author Eugene Valchkou REST api for chat messaging
 */
@RestController
@RequestMapping(value = "**/message")
public class MessageController {
    private static final Logger log = Logger.getLogger(MessageController.class.getName());

    @Autowired
    MessageService              messageService;

    @Autowired
    ModelConvertor              convertor;

    /** guest reads messages */
    @RequestMapping(value = "/guest/{guestId}/gt/{lastId}", method = RequestMethod.GET)
    public ResponseWrapper<List<MessageUI>> readMessagesByGuest(@PathVariable String guestId, @PathVariable UUID lastMsgId) {
        try {
            log.info("read messages by guest " + guestId + " after:" + lastMsgId);
            List<Message> ms = messageService.readMessagesByGuest(guestId, lastMsgId);
            List<MessageUI> result = convertor.messagesToUI(ms);
            log.info("retrieved " + result.size() + " messages by guest " + guestId);
            return new ResponseWrapper<List<MessageUI>>(result);
        } catch (Exception e) {
            return new ResponseWrapper<List<MessageUI>>(ResponseCode.BAD_REQUEST, e.getMessage());
        }
    }

    @RequestMapping(value = "/guest/{guestId}", method = RequestMethod.GET)
    public ResponseWrapper<List<MessageUI>> readMessagesByGuest(@PathVariable String guestId) {
        return readMessagesByGuest(guestId, null);
    }

    /** operator reads messages */
    @RequestMapping(value = "/operator/{operatorId}/gt/{lastMsgId}", method = RequestMethod.GET)
    public ResponseWrapper<List<MessageUI>> readMessagesByOperator(@PathVariable String operatorId, @PathVariable UUID lastMsgId) {
        try {
            log.info("read messages by operator " + operatorId + " after:" + lastMsgId);
            List<Message> ms = messageService.readMessagesByOperator(operatorId, lastMsgId);
            List<MessageUI> result = convertor.messagesToUI(ms);
            log.info("retrieved " + result.size() + " messages by operator " + operatorId);
            return new ResponseWrapper<List<MessageUI>>(result);
        } catch (Exception e) {
            return new ResponseWrapper<List<MessageUI>>(ResponseCode.BAD_REQUEST, e.getMessage());
        }
    }

    /** operator reads messages */
    @RequestMapping(value = "/operator/{operatorId}", method = RequestMethod.GET)
    public ResponseWrapper<List<MessageUI>> readMessagesByOperator(@PathVariable String operatorId) {
        return readMessagesByOperator(operatorId, null);
    }

    /** Post a new message */
    @RequestMapping(value = "/guest/{authorId}", method = RequestMethod.POST)
    public ResponseWrapper<MessageUI> postMessageByGuest(@PathVariable String authorId, @RequestParam String text) {
        try {
            log.info("post message by guest " + authorId);
            Message m = messageService.publishMessageByGuest(authorId, text);
            return new ResponseWrapper<MessageUI>(convertor.messageToUI(m));
        } catch (Exception e) {
            return new ResponseWrapper<MessageUI>(ResponseCode.BAD_REQUEST, e.getMessage());
        }
    }

    /** Post a new message */
    @RequestMapping(value = "/operator/{authorId}/chatroom/{chatRoomId}", method = RequestMethod.POST)
    public ResponseWrapper<MessageUI> postMessageByOperator(@PathVariable String authorId, @PathVariable String chatRoomId, @RequestParam String text) {
        try {
            log.info("post message by operator " + authorId);
            Message m = messageService.publishMessage(authorId, 1, chatRoomId, text);
            return new ResponseWrapper<MessageUI>(convertor.messageToUI(m));
        } catch (Exception e) {
            return new ResponseWrapper<MessageUI>(ResponseCode.BAD_REQUEST, e.getMessage());
        }
    }
}
