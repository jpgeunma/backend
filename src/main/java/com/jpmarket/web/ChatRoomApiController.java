package com.jpmarket.web;

import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.config.jwt.JwtUtils;
import com.jpmarket.domain.chat.ChatRoom;
import com.jpmarket.domain.chat.Message;
import com.jpmarket.domain.posts.Posts;
import com.jpmarket.domain.user.User;
import com.jpmarket.service.ChatRoomService;
import com.jpmarket.service.MessageService;
import com.jpmarket.service.PostsService;
import com.jpmarket.service.UserService;
import com.jpmarket.web.chatDto.ChatNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    private UserService userService;

    @Autowired
    private PostsService postsService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JwtUtils jwtUtils;


    @MessageMapping("/chat")
    public void processMessage(@Payload Message message, @Header("Authorization") String token) {
        Long senderId = message.getSenderId();
        Long postId = message.getPostId();
        Long receiverId = postsService.findById(postId).getUserId();
        Long chatId;
        System.out.println("processMessage " + message.toString());
        // sender is buyer
        if(senderId.equals(receiverId)) {
            chatId = chatRoomService.getChatIdByBuyerId(senderId, postId);
            // receiver id should be seller
            receiverId = chatRoomService.getChatRoomById(chatId).getSellerId();
        }
        // sender is seller
        else{
            chatId = chatRoomService.getChatIdBySellerId(senderId, postId);
        }

        System.out.println("processMessage token " + token + " postId " + postId + " chatId " + chatId + ", senderId " + senderId + ", receiverId  " + receiverId);

        // chatRoom is not exist
        ChatRoom chatRoom;
        if(chatId.equals(-1L)) {
            // sender is buyer
            if(senderId.equals(receiverId)) {
                chatRoom = chatRoomService.createChat(receiverId, senderId, postId);
            }
            // sender is seller
            else {
                chatRoom = chatRoomService.createChat(senderId, receiverId, postId);
            }
            message.setChatId(chatRoom.getId());
        }
        else{
            message.setChatId(chatId);
        }
        message.setReceiverId(receiverId);
        Message saved = messageService.save(message);
        messagingTemplate.convertAndSendToUser(
                //message.getReceiverId().toString(), "/queue/messages",
                saved.getReceiverId().toString(), "/queue/messages",
                ChatNotification.builder()
                        .id(saved.getId())
                        .senderId(saved.getSenderId())
                        .senderName(saved.getSenderName())
                        .build());
    }

    @GetMapping("/messages/{senderId}{postId}/count")
    public ResponseEntity<Long> countNewMessages(@PathVariable Long senderId , @PathVariable Long postId){
        Posts posts = postsService.findById(postId).toEntity();
        Long receiverId = postsService.findById(postId).getBuyerId();
        System.out.println("countNewMessages senderId: " + senderId + " postId: " + postId);
        // sender is buyer
        if(senderId.equals(receiverId)) {
            // receiver id should be seller
            Long chatId = chatRoomService.getChatIdByBuyerId(senderId, postId);
            receiverId = chatRoomService.getChatRoomById(chatId).getSellerId();
        }

        return ResponseEntity.ok(messageService.countNewMessage(senderId, receiverId));
    }

    @GetMapping("/messages/{senderId}/{postId}")
    public ResponseEntity<?> findChatMessages(@PathVariable Long senderId, @PathVariable Long postId) {
        Posts posts = postsService.findById(postId).toEntity();
        Long receiverId = postsService.findById(postId).getBuyerId();
        Long sellerId, buyerId;
        System.out.println("findChatMessages senderId: " + senderId + " postId: " + postId);
        // if sender is buyer
        // TODO need to be fix ---> use postId
        if(senderId.equals(receiverId)) {
            // receiver id should be seller
            Long chatId = chatRoomService.getChatIdByBuyerId(senderId, postId);
            receiverId = chatRoomService.getChatRoomById(chatId).getSellerId();
            sellerId = receiverId;
            buyerId = senderId;
            return ResponseEntity.ok(messageService.findChatMessages(sellerId, buyerId));
        }
        sellerId = senderId;
        buyerId = receiverId;
        return ResponseEntity.ok(messageService.findChatMessages(sellerId, buyerId));

    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.findById(id));
    }

//    @MessageMapping("/chat")
//    public void processMessage(@Payload Message message) {
//        System.out.println("processMessage " + message.getContent() + ", " + message.getReceiverName() + ", " + message.getSenderName() + ", " + message.getReceiverId() + ", " + message.getSenderId());
//        Long chatId = chatRoomService.getChatId(message.getSenderId(), message.getReceiverId());
//        if(chatId.equals(-1L)) {
//            ChatRoom chatRoom = chatRoomService.createChat(message.getSenderId(), message.getReceiverId());
//            message.setChatId(chatRoom.getId());
//        }
//        else{
//            message.setChatId(chatId);
//        }
//
//        Message saved = messageService.save(message);
//        messagingTemplate.convertAndSendToUser(
//                message.getReceiverId().toString(), "/queue/messages",
//                ChatNotification.builder()
//                        .id(saved.getId())
//                        .senderId(saved.getSenderId())
//                        .senderName(saved.getSenderName())
//                        .build());
//    }
//
//    @GetMapping("/messages/{senderId}/{receiverId}/count")
//    public ResponseEntity<Long> countNewMessages(@PathVariable Long senderId, @PathVariable Long receiverId){
//        System.out.println("countNewMessages senderId: " + senderId + " receiverId: " + receiverId);
//        return ResponseEntity.ok(messageService.countNewMessage(senderId, receiverId));
//    }
//
//    @GetMapping("/messages/{senderId}/{receiverId}")
//    public ResponseEntity<?> findChatMessages(@PathVariable Long senderId, @PathVariable Long postId) {
//        System.out.println("findChatMessages senderId: " + senderId + " receiverId: " + postId);
//
//        Posts posts = postsService.findById(postId).toEntity();
//
//        // sender is writer
//        if(senderId.equals(posts.getUserId())){
//            return ResponseEntity.ok(messageService.findChatMessages(senderId, postId));
//        }
//        // sender is buyer
//        else{
//            return ResponseEntity.ok(messageService.findChatMessages(senderId, postId));
//        }
//    }
//
//    @GetMapping("/messages/{id}")
//    public ResponseEntity<?> findMessage(@PathVariable Long id) {
//        return ResponseEntity.ok(messageService.findById(id));
//    }

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
