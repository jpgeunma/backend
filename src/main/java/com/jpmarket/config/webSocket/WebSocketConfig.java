package com.jpmarket.config.webSocket;


import com.jpmarket.config.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.util.MimeTypeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JwtUtils jwtUtils;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        logger.debug(config.toString());
        config.enableSimpleBroker( "/user", "/chatroom");
        config.setApplicationDestinationPrefixes("/webs");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        logger.debug(registry.toString());
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        logger.debug(messageConverters.toString());
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }

    // REFERENCE https://velog.io/@tlatldms/Spring-Boot-STOMP-JWT-Socket-%EC%9D%B8%EC%A6%9D%ED%95%98%EA%B8%B0

//    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                System.out.println("StompHeaderAccessor " + accessor.toString());
                System.out.println("message " + message.toString());
                System.out.println("NativeHeader " + accessor.getFirstNativeHeader("Authorization"));
                if (StompCommand.CONNECT.equals(accessor.getCommand())){
                    String Jwt = accessor.getFirstNativeHeader("Authorization");
                    jwtUtils.validateJwtToken(Jwt.substring(7, Jwt.length()));
                }
                return message;
            }
        });
    }


}
