package com.taehun.boardChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taehun.boardChat.dto.Message;
import com.taehun.boardChat.service.ChatService;

@Controller
public class ChatController {

	@RequestMapping("/chat")
	public String chatting() throws Exception {
		return "chat";
	}
	
	@MessageMapping("/in")	// Websocketconfig���� ������ prefix�� �����ϸ� /app/in
	@SendTo("/topic/in")	// �����Ϸ��� ���� subscribe
	public String chatIn(String message) throws Exception {
		System.out.println(message);
		return message;
	}
	
	@MessageMapping("/talk")
	@SendTo("/topic/talk")
	public Message broadcasting(Message message) throws Exception {
		return message;
	}
	
	@MessageMapping("/out")
	@SendTo("/topic/out")
	public String chatOut(String message) throws Exception {
		System.out.println(message);
		return message;
	}
	
}
