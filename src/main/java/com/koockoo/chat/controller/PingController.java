package com.koockoo.chat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.ResponseWrapper;

@RestController
@RequestMapping(value = "**/ping")
public class PingController {

    /** to check service availability */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseWrapper<String> ping() {
        return new ResponseWrapper<>();
    }
}
