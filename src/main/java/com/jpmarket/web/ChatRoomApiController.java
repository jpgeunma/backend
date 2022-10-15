package com.jpmarket.web;

import com.jpmarket.domain.chat.ChatRoom;
import com.jpmarket.domain.chat.Message;
import com.jpmarket.service.ChatRoomService;
import com.jpmarket.service.MessageService;
import com.jpmarket.web.chatDto.ChatNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ChatRoomApiController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatRoomService chatRoomService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        System.out.println("processMessage " + message.getContent() + ", " + message.getReceiverName() + ", " + message.getSenderName() + ", " + message.getReceiverId() + ", " + message.getSenderId());
        Long chatId = chatRoomService.getChatId(message.getSenderId(), message.getReceiverId());
        if(chatId.equals(-1L)) {
            ChatRoom chatRoom = chatRoomService.createChat(message.getSenderId(), message.getReceiverId());
            message.setChatId(chatRoom.getId());
        }
        else{
            message.setChatId(chatId);
        }

        Message saved = messageService.save(message);
        messagingTemplate.convertAndSendToUser(
                message.getReceiverId().toString(), "/queue/messages",
                ChatNotification.builder()
                        .id(saved.getId())
                        .senderId(saved.getSenderId())
                        .senderName(saved.getSenderName())
                        .build());
    }

    @GetMapping("/messages/{senderId}/{receiverId}/count")
    public ResponseEntity<Long> countNewMessages(@PathVariable Long senderId, @PathVariable Long receiverId){
        System.out.println("countNewMessages senderId: " + senderId + " receiverId: " + receiverId);
        return ResponseEntity.ok(messageService.countNewMessage(senderId, receiverId));
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<?> findChatMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        System.out.println("findChatMessages senderId: " + senderId + " receiverId: " + receiverId);
        return ResponseEntity.ok(messageService.findChatMessages(senderId, receiverId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.findById(id));
    }

    // TODO ============================================ for test ===================
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        logger.info("message:" + message.toString() + ", " + message.getContent());
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        logger.info("private message: " + message.toString() + ", " + message.getContent());
        messagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }
    // =============================================================================
}
