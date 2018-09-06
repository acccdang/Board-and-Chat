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
		config.enableSimpleBroker("/topic");	//  브로커를 이용가능하게 설정. 메모리 기반의 메시지 브로커가 해당 API를 구독하고 있는 클라이언트에게 메시지를 전달함.
		config.setApplicationDestinationPrefixes("/app");	// 서버에서 클라이언트로부터 메시지를 받을 API의 preifx를 설정.
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("webSocket").withSockJS();	// 웹 소켓에서 활용될 주소를 적어주고, withSockJs를 이용하여 향상된 SockJs를 사용.
	}
}
