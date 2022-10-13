package com.jpmarket.web;

import com.jpmarket.domain.chat.Message;
import com.jpmarket.service.ChatRoomService;
import com.jpmarket.service.MessageService;
import com.jpmarket.web.chatDto.ChatNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatRoomApiController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        System.out.println("processMessage " + message);
        Long chatId = chatRoomService.getChatId(message.getSenderId(), message.getReceiverId(), true);
        message.setChatId(chatId);

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
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        messagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }
    // =============================================================================
}
