package com.koockoo.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.ResponseWrapper;
import com.koockoo.chat.simulate.ChatSimulator;

@RestController
@RequestMapping(value = "**/sim")
public class SimulatorController {
    
    @Autowired
    private ChatSimulator simulator; 

    @RequestMapping(value = "/guest/{chatRoomId}/{operatorRef}", method = RequestMethod.GET)
    public ResponseWrapper<String> talkToGuest(@PathVariable String chatRoomId, @PathVariable String operatorRef) {
        simulator.startTalkingToGuest(chatRoomId, operatorRef);
        return new ResponseWrapper<>();
    }
    
    @RequestMapping(value = "/operator/{topicRef}", method = RequestMethod.GET)
    public ResponseWrapper<String> talkToOperator(@PathVariable String topicRef) {
        simulator.startTalkingToOperator(topicRef);
        return new ResponseWrapper<>();
    }    
}
