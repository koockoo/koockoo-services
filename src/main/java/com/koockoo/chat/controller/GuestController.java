package com.koockoo.chat.controller;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "**/guest")
public class GuestController {

	private static final Logger log = Logger.getLogger(GuestController.class.getName());
	
//	@Autowired
//	private GuestService guestService;
//
//	/** operator or guest exits chat */
//	@RequestMapping(value = "/exit/{contactId}", method = RequestMethod.GET) 
//	@ResponseStatus(value = HttpStatus.OK)
//    public void exit(@PathVariable String contactId) {
//		guestService.exitChat(contactId);
//	}	
//	
//	/** guest initiates new chat */
//	@RequestMapping(value = "/start/{accountId}", method = RequestMethod.GET)
//	@ResponseBody
//	public ChatSession start(@PathVariable String accountId, @RequestParam String name) {
//		log.info("doStart");
//		ChatSession session = guestService.startChat(name, accountId);
//		return session;
//	}	
//	
//	/** operator or guest exits chat */
//	@RequestMapping(value = "/isaccepted/{sessionId}", method = RequestMethod.GET) 
//    public @ResponseBody ResponseWrapper<ChatSession> isAccepted(@PathVariable String sessionId) {
//		ChatSession session = guestService.isSessionAccepted(sessionId);
//		if (session == null) {
//			log.info("isaccepted session is null");
//			return new ResponseWrapper<ChatSession>(ResponseCode.BAD_REQUEST, "Session not found:"+sessionId);
//		} else {
//			log.info("isaccepted session:"+session.getId());
//			return new ResponseWrapper<ChatSession>();
//		}
//	}	

}
