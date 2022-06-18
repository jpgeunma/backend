package com.jpmarket.web;

import com.jpmarket.domain.chatroom.ChatRoom;
import com.jpmarket.domain.chatroom.ChatRoomRepository;
import com.jpmarket.domain.chatroom.messages.Messages;
import com.jpmarket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;


@RestController
@RequestMapping("/sse-server")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class ChatRoomApiController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/user")
    public String addUser(@RequestParam("name") String name) {
        chatRoomService.addUser(0L, name);
        return "User" + name + " added !";
    }

    @PostMapping("/user/messages")
    public String sendUserMessage(@RequestBody Messages messages) {
        chatRoomService.sendUserMessage(messages);
        return "Message sent from " + messages.getSender() + " to " + messages.getReceiver();
    }

    @GetMapping("/user")
    public Flux<ServerSentEvent<List<String>>> streamUsers(@RequestParam("name") String name) {
        return chatRoomService.getUsers(name);
    }

    @GetMapping("/user/messages")
    public Flux<ServerSentEvent<Messages>> streamLastMessages(@RequestParam("name") String name) {
        return chatRoomService.getLastUserMessage(name);
    }

    @GetMapping("/user/messages/all")
    public Flux<ServerSentEvent<List<Messages>>> streamMessages(@RequestParam("name") String name) {
        return chatRoomService.getAllUserMessages(name);
    }




}
