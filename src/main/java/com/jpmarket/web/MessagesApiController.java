package com.jpmarket.web;

import com.jpmarket.domain.chatroom.messages.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessagesApiController {
//    private final SimpMessageSendingOperations sendingOperations;
//
//    @MessageMapping("/chat/message")
//    public void enter(Messages messages) {
//        sendingOperations.convertAndSend("/chat/room/" + messages.getRoomId(), messages);
//    }
}
