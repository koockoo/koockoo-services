package com.koockoo.chat.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "**/operator")
public class OperatorController {
	
	private static final Logger log = Logger.getLogger(OperatorController.class.getName());

//	@Autowired
//	OperatorService operatorService;
//
//	@RequestMapping(value = "login", method = RequestMethod.GET)
//    public @ResponseBody ResponseWrapper<List<ChatOperator>> login(@RequestParam String login, @RequestParam String token) {
//        ChatOperator oper = operatorService.login(login, token);
//        List<ChatOperator> data = new ArrayList<>();
//        data.add(oper);
//        return new ResponseWrapper<List<ChatOperator>>(data);
//	}
//	
//	@RequestMapping(value = "logout/{contact}", method = RequestMethod.POST)
//	@ResponseStatus(value = HttpStatus.OK)
//	public void logout(@PathVariable String contact) {
//        operatorService.logout(contact);
//	}
//	
//	@RequestMapping(value = "join/{operatorId}/{sessionId}", method = RequestMethod.POST)
//	@ResponseStatus(value = HttpStatus.OK)
//	public void join(@PathVariable String operatorId, @PathVariable String sessionId) {
//		log.info("join");
//		operatorService.joinSession(sessionId, operatorId);
//	}	
//	
//	/** operator pings for new guests to chat with*/
//	@RequestMapping(value = "ping/{contact}", method = RequestMethod.GET)
//	public @ResponseBody ResponseWrapper<List<ChatSession>> ping(@PathVariable String contact) {
//		List<ChatSession> data = operatorService.pingNewSessions(contact);
//		return new ResponseWrapper<List<ChatSession>>(data);
//	}
//	
//	/** operator pings for new guests to chat with*/
//	@RequestMapping(value = "active/{operatorId}", method = RequestMethod.GET)
//	public @ResponseBody ResponseWrapper<List<ChatSession>> getActiveSessions(@PathVariable String operatorId) {
//		List<ChatSession> data = operatorService.getActiveSessions(operatorId);
//		return new ResponseWrapper<List<ChatSession>>(data);
//	}		
}
