package com.jpmarket.web;

import com.jpmarket.domain.chatroom.message.Message;
import com.jpmarket.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RestController
@RequestMapping("/sse-server")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class ChatRoomApiController {

    @Autowired
    private ChatRoomService chatRoomService;

//    @PostMapping("/user")
//    public String addUser(@RequestParam("name") String name) {
//        chatRoomService.addUser(0L, name);
//        return "User" + name + " added !";
//    }

//    @PostMapping("/user/messages")
//    public String sendUserMessage(@RequestBody Message messages) {
//        chatRoomService.sendUserMessage(messages);
//        return "Message sent from " + messages.getSender() + " to " + messages.getSender();
//    }


//
//    @GetMapping("/user")
//    public Flux<ServerSentEvent<List<String>>> streamUsers(@RequestParam("name") String name) {
//        return chatRoomService.getUsers(name);
//    }
//
//    @GetMapping("/user/messages")
//    public Flux<ServerSentEvent<Message>> streamLastMessages(@RequestParam("name") String name) {
//        return chatRoomService.getLastUserMessage(name);
//    }
//
//    @GetMapping("/user/messages/all")
//    public Flux<ServerSentEvent<List<Message>>> streamMessages(@RequestParam("name") String name) {
//        return chatRoomService.getAllUserMessages(name);
//    }
//



}
