package com.koockoo.chat.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koockoo.chat.model.ResponseCode;
import com.koockoo.chat.model.ResponseWrapper;
import com.koockoo.chat.model.db.Auth;
import com.koockoo.chat.model.ui.AuthUI;
import com.koockoo.chat.service.AuthService;
import com.koockoo.chat.service.ModelConvertor;

@RestController
@RequestMapping(value = "**/auth")
public class AuthController {

    private static final Logger log = Logger.getLogger(AuthController.class.getName());

    @Autowired
    AuthService                 authService;

    @Autowired
    ModelConvertor              convertor;

    /** Operator Signin with credentials */
    @RequestMapping(value = "signin/operator", method = RequestMethod.POST)
    public ResponseWrapper<AuthUI> operatorSignin(@RequestParam String login, @RequestParam String password) {
        log.info("operator login attempt by : " + login);
        AuthUI auth = null;
        try {
            Auth e = authService.operatorSignin(login, password);
            auth = convertor.authToUI(e);
        } catch (Exception e) {
            log.info("operator login unexpected error : " + login);
            return new ResponseWrapper<AuthUI>(ResponseCode.INTERNAL_ERROR, e.getMessage());
        }
        if (auth == null) {
            log.info("operator login invalid by : " + login);
            return new ResponseWrapper<AuthUI>(ResponseCode.INVALID_CREDENTIALS, "Invalid email or password");
        }
        log.info("operator login success by : " + login);
        return new ResponseWrapper<AuthUI>(auth);
    }

    /** Guest Signin with name */
    @RequestMapping(value = "signin/guest/{topicRef}", method = RequestMethod.POST)
    public ResponseWrapper<AuthUI> guestSignin(@RequestParam String displayName, @PathVariable String topicRef) {
        AuthUI auth = null;
        try {
            log.info("guest sigin by : " + displayName);
            Auth e = authService.guestSignin(displayName, topicRef);
            auth = convertor.authToUI(e);
            log.info("guest sigin success by : " + displayName);
        } catch (Exception e) {
            return new ResponseWrapper<AuthUI>(ResponseCode.INTERNAL_ERROR, e.getMessage());
        }
        return new ResponseWrapper<AuthUI>(auth);
    }

    /** Signout. Invalidate auth */
    @RequestMapping(value = "signout", method = RequestMethod.POST)
    public ResponseWrapper<String> signout(@RequestParam String authId) {
        try {
            authService.signout(authId);
        } catch (Exception e) {
            // ignore, who cares
        }
        return new ResponseWrapper<>();
    }

    /** Send user/pwd on email */
    @RequestMapping(value = "recover", method = RequestMethod.POST)
    public ResponseWrapper<String> recover(@RequestParam String email) {
        // TODO: implement
        return new ResponseWrapper<>();
    }

    /** Unregister remove email/pwd Credentials */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseWrapper<String> delete() {
        // TODO: implement
        return new ResponseWrapper<>();
    }

    /** to check service availability */
    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public ResponseWrapper<String> ping() {
        return new ResponseWrapper<>();
    }
}
