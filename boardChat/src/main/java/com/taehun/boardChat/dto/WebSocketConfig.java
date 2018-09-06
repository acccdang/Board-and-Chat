package com.taehun.boardChat.dto;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");	//  ���Ŀ�� �̿밡���ϰ� ����. �޸� ����� �޽��� ���Ŀ�� �ش� API�� �����ϰ� �ִ� Ŭ���̾�Ʈ���� �޽����� ������.
		config.setApplicationDestinationPrefixes("/app");	// �������� Ŭ���̾�Ʈ�κ��� �޽����� ���� API�� preifx�� ����.
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("webSocket").withSockJS();	// �� ���Ͽ��� Ȱ��� �ּҸ� �����ְ�, withSockJs�� �̿��Ͽ� ���� SockJs�� ���.
	}
}
