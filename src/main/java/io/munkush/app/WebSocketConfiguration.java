package io.munkush.app;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/broker"); // when Subscribe "/broker/toSendMessage or "/broker/{receiverName}/queue/private"
        config.setApplicationDestinationPrefixes("/prefix"); // when stompClient.send("/prefix/getMessage".. or "../getMessageForOne
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/tic_tac_toe") // when connect SockJS new SockJS('/chat');
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}

